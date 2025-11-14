package com.dinonews.controller;

import com.dinonews.model.Article;
import com.dinonews.model.Comment;
import com.dinonews.model.User;
import com.dinonews.repository.ArticleRepository;
import com.dinonews.repository.CommentRepository;
import com.dinonews.repository.UserRepository;
import com.dinonews.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('LECTOR') or hasRole('EDITOR') or hasRole('ADMIN')")
public class UserController {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        // Obtener artículos recientes
        List<Article> recentArticles = articleRepository.findTop5ByPublishedTrueOrderByCreatedAtDesc();
        
        // Obtener comentarios del usuario
        List<Comment> userComments = commentRepository.findTop5ByAuthorOrderByCreatedAtDesc(user);
        
        model.addAttribute("user", user);
        model.addAttribute("recentArticles", recentArticles);
        model.addAttribute("userComments", userComments);
        model.addAttribute("totalArticles", articleRepository.countByPublishedTrue());
        model.addAttribute("userCommentsCount", commentRepository.countByAuthor(user));
        
        return "user/dashboard";
    }

    @PostMapping("/comments")
    public String addComment(@Valid @ModelAttribute("newComment") Comment comment,
                            BindingResult result,
                            @RequestParam Long articleId,
                            @AuthenticationPrincipal UserPrincipal userPrincipal,
                            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error en el comentario");
            return "redirect:/news/" + articleId;
        }
        
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isEmpty()) {
            return "redirect:/";
        }
        
        User user = userRepository.findById(userPrincipal.getId()).orElse(null);
        if (user == null) {
            return "redirect:/auth/login";
        }
        
        Article article = articleOpt.get();
        comment.setArticle(article);
        comment.setAuthor(user);
        
        commentRepository.save(comment);
        
        redirectAttributes.addFlashAttribute("message", "Comentario agregado exitosamente");
        return "redirect:/news/" + articleId;
    }

    @PostMapping("/comments/{id}/delete")
    public String deleteComment(@PathVariable Long id,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               RedirectAttributes redirectAttributes) {
        
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return "redirect:/";
        }
        
        Comment comment = commentOpt.get();
        
        // Verificar que el usuario sea el autor del comentario
        if (!comment.getAuthor().getId().equals(userPrincipal.getId())) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para eliminar este comentario");
            return "redirect:/news/" + comment.getArticle().getId();
        }
        
        Long articleId = comment.getArticle().getId();
        commentRepository.delete(comment);
        
        redirectAttributes.addFlashAttribute("message", "Comentario eliminado exitosamente");
        return "redirect:/news/" + articleId;
    }
}