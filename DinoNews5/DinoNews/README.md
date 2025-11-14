# DinoNews - Página de Noticias de Videojuegos

DinoNews es una aplicación web desarrollada con Spring Boot que funciona como una página de noticias especializada en videojuegos. La aplicación implementa un sistema de roles con tres tipos de usuarios diferentes.

## 🎮 Características

### Roles de Usuario
- **Lector**: Puede leer noticias y comentar en las publicaciones
- **Editor**: Puede publicar y editar sus propias noticias (no las de otros editores)
- **Admin**: Administra cuentas de usuarios y supervisa el contenido

### Funcionalidades
- ✅ Sistema de autenticación y autorización
- ✅ Gestión de noticias con categorías
- ✅ Sistema de comentarios
- ✅ Panel de administración
- ✅ Búsqueda de noticias
- ✅ Filtrado por categorías
- ✅ Interfaz responsive con Bootstrap

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot 3.1.5
- **Seguridad**: Spring Security
- **Base de Datos**: H2 (desarrollo), Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5
- **Build Tool**: Maven
- **Java**: 17

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior

## 🚀 Instalación y Configuración

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd DinoNews
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a la aplicación**
   - URL: http://localhost:8080
   - Consola H2: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Usuario: `sa`
     - Contraseña: (vacía)

## 👥 Usuarios de Prueba

La aplicación se inicializa automáticamente con los siguientes usuarios de prueba:

| Rol | Usuario | Contraseña | Email |
|-----|---------|------------|-------|
| Admin | `admin` | `admin123` | admin@dinonews.com |
| Editor | `editor` | `editor123` | editor@dinonews.com |
| Lector | `lector` | `lector123` | lector@dinonews.com |

## 📱 Uso de la Aplicación

### Para Lectores
1. Registrarse o iniciar sesión
2. Navegar por las noticias en la página principal
3. Leer artículos completos
4. Comentar en las noticias
5. Buscar noticias por palabras clave
6. Filtrar por categorías

### Para Editores
1. Iniciar sesión con credenciales de editor
2. Acceder al panel de editor desde el menú
3. Crear nuevas noticias
4. Editar solo sus propias publicaciones
5. Gestionar el estado de publicación

### Para Administradores
1. Iniciar sesión como administrador
2. Acceder al panel de administración
3. Gestionar usuarios (habilitar/deshabilitar)
4. Modificar roles de usuarios
5. Supervisar todas las noticias
6. Moderar contenido

## 📁 Estructura del Proyecto

```
src/main/java/com/dinonews/
├── component/          # Inicializadores de datos
├── config/            # Configuraciones de seguridad
├── controller/        # Controladores REST y Web
├── model/             # Entidades JPA
├── repository/        # Repositorios de datos
└── security/          # Clases de seguridad

src/main/resources/
├── templates/         # Plantillas Thymeleaf
│   ├── auth/         # Páginas de autenticación
│   ├── editor/       # Panel de editor
│   └── admin/        # Panel de administración
└── static/           # Recursos estáticos
```

## 🔧 Configuración

### Base de Datos
La aplicación usa H2 en memoria por defecto. Para usar una base de datos persistente, modifica `application.properties`:

```properties
# Para MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/dinonews
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### Configuración de Seguridad
Las rutas están protegidas según los roles:
- `/admin/**` - Solo ADMIN
- `/editor/**` - EDITOR y ADMIN
- `/user/**` - LECTOR, EDITOR y ADMIN
- Rutas públicas: `/`, `/news/**`, `/auth/**`

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Ejecutar con coverage
mvn test jacoco:report
```

## 📦 Build y Deployment

```bash
# Crear JAR ejecutable
mvn clean package

# Ejecutar JAR
java -jar target/dinonews-0.0.1-SNAPSHOT.jar
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 🐛 Reportar Bugs

Si encuentras un bug, por favor abre un issue describiendo:
- Pasos para reproducir el error
- Comportamiento esperado vs actual
- Screenshots si es necesario
- Información del entorno (OS, Java version, etc.)

## 📧 Contacto

- Desarrollador: [Tu Nombre]
- Email: tu-email@ejemplo.com
- Proyecto: [Link al repositorio]

---

⭐ ¡No olvides dar una estrella al proyecto si te ha sido útil!