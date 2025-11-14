package com.dinonews.component;

import com.dinonews.model.Article;
import com.dinonews.model.Role;
import com.dinonews.model.User;
import com.dinonews.repository.ArticleRepository;
import com.dinonews.repository.RoleRepository;
import com.dinonews.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }
    
    private void initializeData() {
        try {
            // Crear roles
            if (roleRepository.count() == 0) {
                Role lectorRole = new Role(Role.RoleName.ROLE_LECTOR);
                Role editorRole = new Role(Role.RoleName.ROLE_EDITOR);
                Role adminRole = new Role(Role.RoleName.ROLE_ADMIN);
                
                roleRepository.save(lectorRole);
                roleRepository.save(editorRole);
                roleRepository.save(adminRole);
                
                log.info("Roles creados");
            }
            
            // Crear usuarios
            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN).get();
                Role editorRole = roleRepository.findByName(Role.RoleName.ROLE_EDITOR).get();
                Role lectorRole = roleRepository.findByName(Role.RoleName.ROLE_LECTOR).get();
                
                // Admin
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@dinonews.com");
                admin.setFullName("Administrador");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.getRoles().add(adminRole);
                userRepository.save(admin);
                
                // Editor
                User editor = new User();
                editor.setUsername("editor");
                editor.setEmail("editor@dinonews.com");
                editor.setFullName("Editor de Prueba");
                editor.setPassword(passwordEncoder.encode("editor123"));
                editor.setEnabled(true);
                editor.getRoles().add(editorRole);
                userRepository.save(editor);
                
                // Lector
                User lector = new User();
                lector.setUsername("lector");
                lector.setEmail("lector@dinonews.com");
                lector.setFullName("Lector de Prueba");
                lector.setPassword(passwordEncoder.encode("lector123"));
                lector.setEnabled(true);
                lector.getRoles().add(lectorRole);
                userRepository.save(lector);
                
                log.info("Usuarios creados");
                
                // Crear artículos
                if (articleRepository.count() == 0) {
                    createSampleArticles(editor);
                    log.info("Artículos creados");
                }
            }
            
        } catch (Exception e) {
            log.error("Error inicializando datos: " + e.getMessage(), e);
        }
    }
    
    private void createSampleArticles(User editor) {
        // Artículo 1
        Article article1 = new Article();
        article1.setTitle("Nuevo DLC para The Legend of Zelda: Tears of the Kingdom");
        article1.setSummary("Nintendo anuncia contenido adicional que expandirá la aventura de Link.");
        article1.setContent("Nintendo ha sorprendido a los fans con el anuncio de un nuevo DLC para The Legend of Zelda: Tears of the Kingdom. Este contenido adicional promete expandir significativamente la experiencia de juego con nuevas mecánicas de construcción, zonas inexploradas de Hyrule, y una historia que continúa después de los eventos del juego principal.");
        article1.setCategory("Aventura");
        article1.setImageUrl("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800");
        article1.setPublished(true);
        article1.setAuthor(editor);
        articleRepository.save(article1);
        
        // Artículo 2
        Article article2 = new Article();
        article2.setTitle("Call of Duty: Modern Warfare III rompe récords de ventas");
        article2.setSummary("El último título de la franquicia alcanza los 100 millones de copias vendidas.");
        article2.setContent("Activision ha anunciado que Call of Duty: Modern Warfare III ha logrado un hito histórico al vender más de 100 millones de copias en solo su primera semana de lanzamiento.");
        article2.setCategory("Acción");
        article2.setImageUrl("https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800");
        article2.setPublished(true);
        article2.setAuthor(editor);
        articleRepository.save(article2);
    }
}