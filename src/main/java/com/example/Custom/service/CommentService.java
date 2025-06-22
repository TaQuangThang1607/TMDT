package com.example.Custom.service;

import com.example.Custom.domain.Comment;
import com.example.Custom.domain.Product;
import com.example.Custom.domain.User;
import com.example.Custom.repository.CommentRepository;
import com.example.Custom.repository.ProductRepository;
import com.example.Custom.repository.UserRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment addComment(Long productId, Long userId, String content, Long parentCommentId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setProduct(product);
        comment.setUser(user);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return commentRepository.findByProductAndParentCommentIsNull(product);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }
     public Page<Comment> getCommentsByProduct(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return commentRepository.findByFilters(product, null, null, pageable);
    }

    public Page<Comment> getAllCommentsWithFilters(Long productId, Long userId, Long parentCommentId, Pageable pageable) {
        Product product = productId != null ? productRepository.findById(productId).orElse(null) : null;
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        Comment parentComment = parentCommentId != null ? commentRepository.findById(parentCommentId).orElse(null) : null;
        return commentRepository.findByFilters(product, user, parentComment, pageable);
    }

    @Transactional
    public void updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(content);
        commentRepository.save(comment);
    }


    @Transactional
    public Comment replyToComment(Long commentId, String content, Long adminId) {
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Comment reply = new Comment();
        reply.setContent(content);
        reply.setProduct(parentComment.getProduct());
        reply.setUser(admin);
        reply.setParentComment(parentComment);

        return commentRepository.save(reply);
    }
}