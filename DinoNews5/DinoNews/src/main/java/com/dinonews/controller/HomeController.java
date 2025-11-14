package com.dinonews.controller;

import com.dinonews.model.Article;
import com.dinonews.model.Comment;
import com.dinonews.repository.ArticleRepository;
import com.dinonews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/")
    public String home(Model model, 
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "6") int size,
                      @RequestParam(required = false) String category,
                      @RequestParam(required = false) String search) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles;
        
        if (search != null && !search.trim().isEmpty()) {
            articles = articleRepository.findByKeywordAndPublishedTrue(search.trim(), pageable);
            model.addAttribute("search", search);
        } else if (category != null && !category.trim().isEmpty()) {
            articles = articleRepository.findByCategoryAndPublishedTrueOrderByCreatedAtDesc(category, pageable);
            model.addAttribute("selectedCategory", category);
        } else {
            articles = articleRepository.findByPublishedTrueOrderByCreatedAtDesc(pageable);
        }
        
        List<String> categories = articleRepository.findDistinctCategories();
        
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articles.getTotalPages());
        
        return "index";
    }

    @GetMapping("/news/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        
        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();
            if (article.isPublished()) {
                List<Comment> comments = commentRepository.findByArticleIdOrderByCreatedAtDesc(id);
                model.addAttribute("article", article);
                model.addAttribute("comments", comments);
                model.addAttribute("newComment", new Comment());
                return "article-detail";
            }
        }
        
        return "redirect:/";
    }
}