# Explicación de la Arquitectura y Configuración de Microservicios

Este documento detalla los aspectos fundamentales de la arquitectura del proyecto `PPPsforMS`, explicando cómo se configuran e interactúan los servicios. Es útil para revisión de equipo y sustentación académica.

## 1. Configuración Centralizada (Config Server)

**¿Cómo funciona?**
En una arquitectura tradicional, cada aplicación guarda sus credenciales (como la contraseña de la base de datos) en su propio archivo `application.properties`. En este sistema, extrajimos toda esa configuración y la pusimos en un único lugar: el **Config Server**.

**El Flujo de Configuración:**
1. El microservicio arranca y revisa su archivo mínimo `application.yml`, donde solo hay dos cosas importantes: el nombre de la aplicación (`spring.application.name`) y la ruta hacia el Config Server (`http://localhost:7071`).
2. El microservicio le pide al Config Server sus configuraciones.
3. El Config Server lee los archivos `.yml` almacenados en `infra/config-repo/` y le entrega al microservicio sus variables exactas dependiendo del perfil activo (`dev` o `prod`).

**Ventajas:** Si la contraseña de la BD cambia, solo se cambia en el `config-repo` sin necesidad de re-compilar los `.jar` de los microservicios.

## 2. Registro y Descubrimiento (Eureka / Registry Server)

**¿Cómo funciona?**
Dado que en la nube (y en Docker) las direcciones IP cambian dinámicamente, los microservicios no pueden usar URLs fijas (como `http://192.168.1.5:8081`) para hablar entre ellos. 

**El Flujo de Descubrimiento:**
1. Cuando un microservicio (como `core-service`) se enciende, se comunica con Eureka (puerto 7081) y se inscribe diciendo: "Hola, soy CORE-SERVICE y estoy en esta dirección".
2. Eureka mantiene una lista (Directorio) actualizada de quién está vivo (`UP`) enviando pulsos (heartbeats) cada pocos segundos.
3. Si levantamos 3 instancias del mismo microservicio, Eureka registra a las 3 bajo el mismo nombre y permite balancear la carga entre ellas.

## 3. Comunicación Inter-Servicio (OpenFeign)

**¿Cómo funciona?**
La regla de oro de los microservicios es que **ninguno puede acceder a la base de datos del otro**. Por lo tanto, si `practice-service` necesita conocer el nombre de un Estudiante, tiene que preguntárselo al `profile-service`.

**El Flujo de Feign:**
1. Implementamos interfaces marcadas con `@FeignClient(name = "profile-service")` en `practice-service`.
2. Esta interfaz actúa como un cliente HTTP declarativo. 
3. Cuando ejecutamos un método de esa interfaz, Feign se conecta a Eureka de forma automática, le pregunta la dirección actual de `profile-service` y le hace una petición HTTP GET interna (completamente invisible para el usuario final).
4. El resultado se junta en el `practice-service` y se devuelve una respuesta consolidada.

## 4. API Gateway (Puerta de Enlace)

**¿Cómo funciona?**
El usuario o frontend (`React`, `Postman`) no debe conocer cuántos microservicios existen internamente ni en qué puertos viven.

**El Flujo del Gateway:**
1. Todos los clientes hacen sus peticiones exclusivamente a una única puerta: El **Gateway** en el puerto `7092`.
2. El Gateway toma la URL (por ejemplo, `/api/v1/auth/users`), busca en su tabla de rutas, y determina que eso le corresponde a `auth-service`.
3. Le pregunta a Eureka por la IP de `auth-service`, y enruta la petición (balanceando carga si hay más de uno).
4. Esta capa permite aplicar Seguridad (JWT) y Control de Tráfico (Rate Limiting) de forma global en un futuro, sin alterar el código de los microservicios.
