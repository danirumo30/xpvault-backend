# XPVault - Backend

**XPVault** es un sistema backend desarrollado con Spring Boot destinado a gestionar almacenamiento seguro de datos, como parte de una solución tipo "vault" de información de tu perfil de steam, peliculas y series.

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.4
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring Mail
- PostgreSQL
- Maven
- Lombok

## ⚙️ Requisitos previos

- JDK 17+
- Maven 3.8+
- PostgreSQL
- (Opcional) Docker y Docker Compose

## 🔧 Configuración

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tuusuario/xpvault.git
   cd xpvault
   ```

2. Crea un archivo `.env` o configura el `application.properties` con los siguientes valores:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/xpvault
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

   spring.mail.host=smtp.tudominio.com
   spring.mail.port=587
   spring.mail.username=tu_correo
   spring.mail.password=tu_password
   ```

3. Instala dependencias y ejecuta la aplicación:

   ```bash
   ./mvnw spring-boot:run
   ```

   O en Windows:

   ```bash
   mvnw.cmd spring-boot:run
   ```

## 📁 Estructura del proyecto

```
xpvault/
├── src/
│   └── main/
│       ├── java/com/xpvault/...  # Código fuente
│       └── resources/
│           └── application.properties
├── pom.xml
├── .env
```

## 🛡️ Seguridad

El backend implementa autenticación y autorización mediante **Spring Security**, con JWT. Asegura el acceso controlado a los recursos y endpoints.

## 🗃️ Base de datos

El sistema utiliza **PostgreSQL** para almacenamiento persistente y está integrado con JPA/Hibernate para el acceso a datos.

## ✉️ Soporte de Email

Incorpora envío de correos (por ejemplo, para verificación de cuenta o recuperación de contraseña) usando **Spring Boot Mail**.

## 🧪 Tests

Puedes ejecutar las pruebas con:

```bash
./mvnw test
```

## 👥 Autores

- Daniel Rubio Mora
- David Calderón Daza
- Manuel Martín Rodríguez

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## 🙋‍♀️ Contribuciones

¡Las contribuciones son bienvenidas! Abre un issue o un pull request con tus mejoras.
