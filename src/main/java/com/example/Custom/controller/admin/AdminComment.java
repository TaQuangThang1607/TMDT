package com.example.Custom.controller.admin;

import com.example.Custom.domain.Comment;
import com.example.Custom.domain.User;
import com.example.Custom.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminComment {
    private final CommentService commentService;

    @GetMapping("/comments")
    public String showCommentManagement(Model model,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @RequestParam(value = "productId", required = false) Long productId,
                                       @RequestParam(value = "userId", required = false) Long userId,
                                       @RequestParam(value = "parentCommentId", required = false) Long parentCommentId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentService.getAllCommentsWithFilters(productId, userId, parentCommentId, pageable);

        model.addAttribute("comments", comments.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("totalItems", comments.getTotalElements());
        model.addAttribute("newReply", new Comment());
        return "admin/comment/comment-management";
    }

    @PostMapping("/comments/{commentId}/update")
    public String updateComment(@PathVariable Long commentId,
                               @RequestParam("content") String content,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        User user = new User();
        Long id = (Long) session.getAttribute("id");
        user.setId(id); 
        if (id == null) {
            redirectAttributes.addFlashAttribute("error", "Access denied. Admin role required.");
            return "redirect:/admin/comments";
        }

        try {
            commentService.updateComment(commentId, content);
            redirectAttributes.addFlashAttribute("success", "Comment updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update comment: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session == null || !isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Access denied. Admin role required.");
            return "redirect:/admin/comments";
        }

        try {
            commentService.deleteComment(commentId);
            redirectAttributes.addFlashAttribute("success", "Comment deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete comment: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    @PostMapping("/comments/{commentId}/reply")
    public String replyToComment(@PathVariable Long commentId,
                                @ModelAttribute("newReply") Comment reply,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session == null || !isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Access denied. Admin role required.");
            return "redirect:/admin/comments";
        }

        try {
            Long adminId = (Long) session.getAttribute("id");
            commentService.replyToComment(commentId, reply.getContent(), adminId);
            redirectAttributes.addFlashAttribute("success", "Reply added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add reply: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "ROLE_ADMIN".equals(role);
    }
}
