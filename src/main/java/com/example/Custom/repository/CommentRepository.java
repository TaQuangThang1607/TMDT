package com.example.Custom.repository;

import com.example.Custom.domain.Comment;
import com.example.Custom.domain.Product;
import com.example.Custom.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductAndParentCommentIsNull(Product product);


     @Query("SELECT c FROM Comment c WHERE " +
           "(:product IS NULL OR c.product = :product) AND " +
           "(:user IS NULL OR c.user = :user) AND " +
           "(:parentComment IS NULL OR c.parentComment = :parentComment)")
    Page<Comment> findByFilters(
            @Param("product") Product product,
            @Param("user") User user,
            @Param("parentComment") Comment parentComment,
            Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.parentComment IS NULL")
    Page<Comment> findRootComments(Pageable pageable);
}