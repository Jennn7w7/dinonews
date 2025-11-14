package com.dinonews.controller;

import com.dinonews.model.Article;
import com.dinonews.model.User;
import com.dinonews.repository.ArticleRepository;
import com.dinonews.repository.UserRepository;
import com.dinonews.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/editor")
@PreAuthorize("hasRole('EDITOR') or hasRole('ADMIN')")
public class EditorController {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                           Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.findByAuthorOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articles.getTotalPages());
        
        return "editor/dashboard";
    }

    @GetMapping("/articles/new")
    public String newArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "editor/article-form";
    }

    @PostMapping("/articles/new")
    public String createArticle(@Valid @ModelAttribute("article") Article article,
                               BindingResult result,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "editor/article-form";
        }
        
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        article.setAuthor(user);
        articleRepository.save(article);
        
        redirectAttributes.addFlashAttribute("message", "Artículo creado exitosamente");
        return "redirect:/editor/dashboard";
    }

    @GetMapping("/articles/{id}/edit")
    public String editArticleForm(@PathVariable Long id,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal,
                                 Model model) {
        
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return "redirect:/editor/dashboard";
        }
        
        Article article = articleOpt.get();
        
        // Verificar que el usuario sea el autor del artículo
        if (!article.getAuthor().getId().equals(userPrincipal.getId())) {
            return "redirect:/editor/dashboard";
        }
        
        model.addAttribute("article", article);
        return "editor/article-form";
    }

    @PostMapping("/articles/{id}/edit")
    public String updateArticle(@PathVariable Long id,
                               @Valid @ModelAttribute("article") Article article,
                               BindingResult result,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "editor/article-form";
        }
        
        Optional<Article> existingArticleOpt = articleRepository.findById(id);
        if (existingArticleOpt.isEmpty()) {
            return "redirect:/editor/dashboard";
        }
        
        Article existingArticle = existingArticleOpt.get();
        
        // Verificar que el usuario sea el autor del artículo
        if (!existingArticle.getAuthor().getId().equals(userPrincipal.getId())) {
            return "redirect:/editor/dashboard";
        }
        
        // Actualizar campos
        existingArticle.setTitle(article.getTitle());
        existingArticle.setSummary(article.getSummary());
        existingArticle.setContent(article.getContent());
        existingArticle.setImageUrl(article.getImageUrl());
        existingArticle.setCategory(article.getCategory());
        existingArticle.setPublished(article.isPublished());
        
        articleRepository.save(existingArticle);
        
        redirectAttributes.addFlashAttribute("message", "Artículo actualizado exitosamente");
        return "redirect:/editor/dashboard";
    }

    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               RedirectAttributes redirectAttributes) {
        
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return "redirect:/editor/dashboard";
        }
        
        Article article = articleOpt.get();
        
        // Verificar que el usuario sea el autor del artículo
        if (!article.getAuthor().getId().equals(userPrincipal.getId())) {
            return "redirect:/editor/dashboard";
        }
        
        articleRepository.delete(article);
        
        redirectAttributes.addFlashAttribute("message", "Artículo eliminado exitosamente");
        return "redirect:/editor/dashboard";
    }
}