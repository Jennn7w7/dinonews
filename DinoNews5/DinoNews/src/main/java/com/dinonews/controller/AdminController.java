package com.dinonews.controller;

import com.dinonews.model.Role;
import com.dinonews.model.User;
import com.dinonews.model.Article;
import com.dinonews.repository.UserRepository;
import com.dinonews.repository.RoleRepository;
import com.dinonews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalUsers = userRepository.count();
        long totalArticles = articleRepository.count();
        
        List<User> recentUsers = userRepository.findAll(PageRequest.of(0, 5)).getContent();
        List<Article> recentArticles = articleRepository.findByPublishedTrueOrderByCreatedAtDesc(PageRequest.of(0, 5)).getContent();
        
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalArticles", totalArticles);
        model.addAttribute("recentUsers", recentUsers);
        model.addAttribute("recentArticles", recentArticles);
        
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/users";
        }
        
        User user = userOpt.get();
        List<Role> allRoles = roleRepository.findAll();
        
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        
        return "admin/user-detail";
    }

    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable Long id, 
                                  RedirectAttributes redirectAttributes) {
        
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/users";
        }
        
        User user = userOpt.get();
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        
        String status = user.isEnabled() ? "habilitado" : "deshabilitado";
        redirectAttributes.addFlashAttribute("message", "Usuario " + status + " exitosamente");
        
        return "redirect:/admin/users/" + id;
    }

    @PostMapping("/users/{id}/update-roles")
    public String updateUserRoles(@PathVariable Long id,
                                 @RequestParam List<Long> roleIds,
                                 RedirectAttributes redirectAttributes) {
        
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/users";
        }
        
        User user = userOpt.get();
        Set<Role> newRoles = new HashSet<>();
        
        for (Long roleId : roleIds) {
            Optional<Role> roleOpt = roleRepository.findById(roleId);
            roleOpt.ifPresent(newRoles::add);
        }
        
        user.setRoles(newRoles);
        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("message", "Roles actualizados exitosamente");
        
        return "redirect:/admin/users/" + id;
    }

    @GetMapping("/articles")
    public String listAllArticles(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.findAll(pageable);
        
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articles.getTotalPages());
        
        return "admin/articles";
    }

    @PostMapping("/articles/{id}/toggle-status")
    public String toggleArticleStatus(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {
        
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return "redirect:/admin/articles";
        }
        
        Article article = articleOpt.get();
        article.setPublished(!article.isPublished());
        articleRepository.save(article);
        
        String status = article.isPublished() ? "publicado" : "despublicado";
        redirectAttributes.addFlashAttribute("message", "Artículo " + status + " exitosamente");
        
        return "redirect:/admin/articles";
    }

    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            return "redirect:/admin/articles";
        }
        
        articleRepository.deleteById(id);
        
        redirectAttributes.addFlashAttribute("message", "Artículo eliminado exitosamente");
        
        return "redirect:/admin/articles";
    }
}