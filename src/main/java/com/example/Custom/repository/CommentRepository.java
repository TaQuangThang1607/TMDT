package com.example.Custom.repository;

import com.example.Custom.domain.Comment;
import com.example.Custom.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductAndParentCommentIsNull(Product product);
}