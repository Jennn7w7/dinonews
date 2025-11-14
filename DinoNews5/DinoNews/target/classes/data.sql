-- Insertar roles iniciales
INSERT INTO roles (name) VALUES ('ROLE_LECTOR'), ('ROLE_EDITOR'), ('ROLE_ADMIN');

-- Insertar usuarios de prueba (contraseñas: admin123, editor123, lector123)
INSERT INTO users (username, email, full_name, password, enabled) VALUES 
('admin', 'admin@dinonews.com', 'Administrador', '$2a$10$kNj2t84.AKsd0CvdnJSNOub/b1MNb11LTFUmcbGnZQfUbT6N0O1my', true),
('editor', 'editor@dinonews.com', 'Editor de Prueba', '$2a$10$h7UOGEd1KoJ8gzxTzN3mp.qqNF8VObbUr6YmkoHdn7bbeCpm3NWiS', true),
('lector', 'lector@dinonews.com', 'Lector de Prueba', '$2a$10$c/K6rmPazJn/zKbzmzCbm.oLYqJzKvOtNuwq9VZZcOv0cRFswZvtS', true);

-- Asignar roles a usuarios
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 3), -- admin -> ROLE_ADMIN
(2, 2), -- editor -> ROLE_EDITOR
(3, 1); -- lector -> ROLE_LECTOR

-- Insertar artículos de prueba
INSERT INTO articles (title, summary, content, category, image_url, published, author_id, created_at, updated_at) VALUES 
('Nuevo DLC para The Legend of Zelda: Tears of the Kingdom', 
 'Nintendo anuncia contenido adicional que expandirá la aventura de Link con nuevas mecánicas y zonas por explorar.',
 'Nintendo ha sorprendido a los fans con el anuncio de un nuevo DLC para The Legend of Zelda: Tears of the Kingdom. Este contenido adicional promete expandir significativamente la experiencia de juego con nuevas mecánicas de construcción, zonas inexploradas de Hyrule, y una historia que continúa después de los eventos del juego principal. El DLC incluirá nuevos materiales para la habilidad de Fuse, dungeons adicionales, y la posibilidad de explorar regiones subterráneas completamente nuevas. Los desarrolladores han confirmado que el contenido estará disponible a principios del próximo año y será gratuito para todos los propietarios del juego base.',
 'Aventura',
 'https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800',
 true,
 2,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Call of Duty: Modern Warfare III rompe récords de ventas',
 'El último título de la franquicia alcanza los 100 millones de copias vendidas en su primera semana.',
 'Activision ha anunciado que Call of Duty: Modern Warfare III ha logrado un hito histórico al vender más de 100 millones de copias en solo su primera semana de lanzamiento. Este éxito convierte al título en el lanzamiento más exitoso de la franquicia hasta la fecha. El juego ha sido especialmente bien recibido por su modo multijugador renovado, que incluye nuevos mapas, modos de juego innovadores, y un sistema de progresión completamente rediseñado. La campaña para un solo jugador también ha recibido elogios por su narrativa cinematográfica y sus mecánicas de juego pulidas. Los desarrolladores ya han confirmado que están trabajando en contenido post-lanzamiento que incluirá nuevos mapas multijugador, eventos especiales, y una expansión de la campaña.',
 'Acción',
 'https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800',
 true,
 2,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('PlayStation 5 Pro: Las especificaciones técnicas reveladas',
 'Sony desvela los detalles técnicos de su nueva consola que promete revolucionar el gaming en 4K y 8K.',
 'Sony Interactive Entertainment ha revelado oficialmente las especificaciones técnicas de la PlayStation 5 Pro, su nueva consola de próxima generación que llegará a las tiendas el próximo año. La consola contará con un procesador AMD Ryzen personalizado de 8 núcleos, una GPU que ofrece un 45% más de potencia de renderizado que la PS5 estándar, y 32GB de RAM GDDR6. Una de las características más destacadas es su capacidad nativa para ejecutar juegos en resolución 8K a 60fps, así como soporte para ray tracing avanzado en tiempo real. La consola también incluirá un SSD de 2TB de alta velocidad y retrocompatibilidad completa con todos los juegos de PS4 y PS5. Sony ha confirmado que más de 50 títulos ya están siendo optimizados para aprovechar al máximo las capacidades de la nueva consola.',
 'Hardware',
 'https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=800',
 true,
 2,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('Indie Game Spotlight: Hollow Knight Silksong finalmente tiene fecha',
 'Team Cherry anuncia la fecha de lanzamiento oficial de la secuela más esperada del metroidvania.',
 'Después de años de espera, Team Cherry ha anunciado oficialmente que Hollow Knight: Silksong estará disponible el 25 de marzo del próximo año. Esta secuela del aclamado metroidvania seguirá las aventuras de Hornet en el reino de Pharloom, un territorio completamente nuevo lleno de misterios por descubrir. El juego presenta más de 150 enemigos únicos, 40 jefes diferentes, y un sistema de combate completamente renovado que aprovecha las habilidades únicas de Hornet. Los desarrolladores han confirmado que Silksong será significativamente más grande que el juego original, con más de 20 horas de contenido principal y docenas de horas adicionales para los completistas. El juego estará disponible en PC, Nintendo Switch, PlayStation, y Xbox, y formará parte del Xbox Game Pass desde el día de lanzamiento.',
 'Indie',
 'https://images.unsplash.com/photo-1511512578047-dfb367046420?w=800',
 true,
 2,
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP);