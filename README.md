# Sistema de Gestión de Prácticas Pre-Profesionales (Microservicios)

Este proyecto es la versión refactorizada a **Microservicios (Spring Cloud)** de un antiguo monolito. Contiene 4 microservicios orquestados junto con infraestructura en la nube.

## 🏗 Arquitectura
- **Infraestructura (`/infra`)**:
  - `config-server` (Puerto: 7071)
  - `registry-server` / Eureka (Puerto: 7081)
  - `gateway` (Puerto: 7091)
- **Microservicios (`/services`)**:
  - `auth-service` (Puerto DEV: 8081) - Gestión de Usuarios
  - `core-service` (Puerto DEV: 8086) - Gestión de Empresas
  - `profile-service` (Puerto DEV: 8085) - Gestión de Perfiles de Estudiantes
  - `practice-service` (Puerto DEV: 8084) - Gestión de Planes de Prácticas y Feign Clients

## ⚙️ Requisitos Previos
Para poder ejecutar este proyecto en cualquier entorno, necesitas tener instalado:
- **Java 17** (JDK)
- **Maven** (o usar el wrapper `mvnw` incluido)
- **Docker y Docker Compose** (Obligatorio para las bases de datos y modo producción)

---

## 🚀 Guía de Despliegue para el Equipo

### 1️⃣ Ejecución en Modo Desarrollo (DEV) - Híbrido
Este modo es ideal para programar y hacer pruebas locales. Las bases de datos correrán en Docker, pero los microservicios se ejecutarán directamente en tu máquina mediante Maven.

**Paso 1: Levantar las bases de datos de Dev**
Abre una terminal y levanta los contenedores de MySQL para cada servicio:
```bash
cd services/auth-service && docker compose -f docker-compose-dev.yml up -d
cd ../core-service && docker compose -f docker-compose-dev.yml up -d
cd ../profile-service && docker compose -f docker-compose-dev.yml up -d
cd ../practice-service && docker compose -f docker-compose-dev.yml up -d
```
*(Los puertos expuestos a tu local serán: 3383, 3386, 3385, 3384)*

**Paso 2: Levantar la Infraestructura Core**
Abre una terminal nueva y levanta los servicios base utilizando el plugin de Spring Boot. ¡Sigue este orden exacto y espera a que cada uno termine de arrancar!
```bash
# 1. Config Server
cd infra/config-server
mvn spring-boot:run -Dspring-boot.run.profiles=native

# 2. Registry Server (Eureka)
cd ../registry-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 3. API Gateway
cd ../gateway
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Paso 3: Levantar los Microservicios**
En otras terminales, levanta los servicios de negocio:
```bash
cd services/auth-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Repite el comando anterior ingresando a las carpetas:
# - services/core-service
# - services/profile-service
# - services/practice-service
```
**Test:** Accede a `http://localhost:8081/swagger-ui.html` para ver los endpoints interactivos de Auth (repite con los otros puertos).

---

### 2️⃣ Ejecución en Modo Producción (PROD) - Full Docker
Este modo es para probar cómo se comportaría el sistema en un servidor real. Absolutamente todo (aplicaciones + bases de datos) se empaqueta en contenedores y se comunican por una red interna de Docker (`ms-net`).

**Paso 1: Compilar los archivos `.jar`**
Antes de construir las imágenes Docker, debes empaquetar el código compilado:
```bash
# Compilar infra
cd infra/config-server && mvn clean package -DskipTests
cd ../registry-server && mvn clean package -DskipTests
cd ../gateway && mvn clean package -DskipTests

# Compilar servicios
cd ../../services/auth-service && mvn clean package -DskipTests
cd ../core-service && mvn clean package -DskipTests
cd ../profile-service && mvn clean package -DskipTests
cd ../practice-service && mvn clean package -DskipTests
```

**Paso 2: Configurar las Variables de Entorno**
Copia el archivo de ejemplo y crea el `.env` real en la **raíz del proyecto**:
```bash
cp .env.example .env
```
*(Abre el archivo `.env` en cualquier editor de texto para verificar las contraseñas si lo deseas)*

**Paso 3: Levantar TODO el ecosistema**
¡Atención! Debes levantar primero la Infraestructura (esperar unos segundos a que Config Server y Eureka estén listos), y luego los microservicios.
```bash
# Levantar infraestructura
cd infra && docker compose up -d --build

# Levantar microservicios
cd ../services/auth-service && docker compose up -d --build
cd ../core-service && docker compose up -d --build
cd ../profile-service && docker compose up -d --build
cd ../practice-service && docker compose up -d --build
```

**Paso 4: Validar en Producción**
- **Eureka:** `http://localhost:7082` (Verifica que todas las instancias estén en estado UP)
- **Gateway:** `http://localhost:7092/api/v1/...` (Úsalo para rutear tus peticiones hacia los microservicios sin exponer sus puertos reales)
