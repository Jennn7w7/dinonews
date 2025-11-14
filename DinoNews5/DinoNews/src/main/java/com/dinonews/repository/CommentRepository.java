package com.dinonews.repository;

import com.dinonews.model.Comment;
import com.dinonews.model.Article;
import com.dinonews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleOrderByCreatedAtDesc(Article article);
    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);
    
    // Métodos para dashboard
    List<Comment> findTop5ByAuthorOrderByCreatedAtDesc(User author);
    Long countByAuthor(User author);
}