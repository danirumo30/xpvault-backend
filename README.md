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

   security.jwt.secret-key=tu_jwt_key_secreta

   steam.api.key=tu_key_steam_api
   tmdb.api.key=tu_key_tmdb_api
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

# XPVault - Backend

**XPVault** es un sistema backend desarrollado con Spring Boot destinado a gestionar almacenamiento seguro de datos, como parte de una solución tipo "vault" de información de tu perfil de steam, películas y series.

## 🚀 Tecnologías utilizadas

* Java 17
* Spring Boot 3.4.4

  * Spring Web
  * Spring Security
  * Spring Data JPA
  * Spring Mail
* PostgreSQL
* Maven
* Lombok

## ⚙️ Requisitos previos

* JDK 17+
* Maven 3.8+
* PostgreSQL
* (Opcional) Docker y Docker Compose

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

* Daniel Rubio Mora
* David Calderón Daza
* Manuel Martín Rodríguez

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## 🤝‍️ Contribuciones

¡Las contribuciones son bienvenidas! Abre un issue o un pull request con tus mejoras.

---

## 🚀 Despliegue en AWS Elastic Beanstalk

Puedes desplegar este backend fácilmente usando **AWS Elastic Beanstalk**. Para ello, asegúrate de tener instalado:

* [Python 3](https://www.python.org/downloads/)
* AWS Elastic Beanstalk CLI (`awsebcli`)

### ✅ Instalación de EB CLI

```bash
pip install awsebcli --upgrade --user
```

> Nota: Asegúrate de tener el binario de Python en tu PATH (puede requerir reiniciar terminal).

### 🔭 Pasos para desplegar

1. **Inicia Elastic Beanstalk en tu proyecto:**

   ```bash
   eb init
   ```

   * Selecciona la región adecuada (ej: `eu-west-1`)
   * Plataforma: `Corretto 21 running on 64bit Amazon Linux 2023`

2. **Crea un entorno (una vez):**

   ```bash
   eb create spring-env
   ```

3. **Agrega un archivo `Procfile`** en la raíz con este contenido:

   ```text
   web: java -jar target/backend-0.0.1-SNAPSHOT.jar
   ```

4. **Agrega un archivo `.ebignore`** en la raíz con este contenido para subir solo lo necesario:

   ```ebignore
   *
   !target/backend-0.0.1-SNAPSHOT.jar
   !Procfile
   ```

5. **Haz el build del proyecto:**

   ```bash
   ./mvnw clean package
   ```

6. **Despliega:**

   ```bash
   eb deploy
   ```

7. **Comprueba el estado de tu aplicación:**

   ```bash
   eb status
   ```

8. **Accede a tu aplicación:**

   ```bash
   eb open
   ```

8. **Comprieba los logs:**

   ```bash
   eb logs
   eb logs --all
   ```

> Asegúrate de configurar las **variables de entorno** necesarias desde la consola de AWS EB (por ejemplo: `SPRING_DATASOURCE_URL`, `JWT_SECRET_KEY`, etc.).

## 👥 Autores

- Daniel Rubio Mora
- David Calderón Daza
- Manuel Martín Rodríguez

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## 🙋‍♀️ Contribuciones

¡Las contribuciones son bienvenidas! Abre un issue o un pull request con tus mejoras.
