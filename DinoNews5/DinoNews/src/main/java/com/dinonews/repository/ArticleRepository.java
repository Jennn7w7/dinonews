package com.dinonews.repository;

import com.dinonews.model.Article;
import com.dinonews.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByPublishedTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Article> findByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);
    Page<Article> findByCategoryAndPublishedTrueOrderByCreatedAtDesc(String category, Pageable pageable);
    
    // Métodos para dashboard
    List<Article> findTop5ByPublishedTrueOrderByCreatedAtDesc();
    Long countByPublishedTrue();
    List<Article> findByAuthorOrderByCreatedAtDesc(User author);
    List<Article> findByAuthor(User author);
    
    @Query("SELECT DISTINCT a.category FROM Article a WHERE a.published = true AND a.category IS NOT NULL AND a.category != ''")
    List<String> findDistinctCategories();
    
    @Query("SELECT a FROM Article a WHERE a.published = true AND " +
           "(LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) ORDER BY a.createdAt DESC")
    Page<Article> findByKeywordAndPublishedTrue(@Param("keyword") String keyword, Pageable pageable);
}