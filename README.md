# Taller Copilot - Spring Batch

## Requisitos

- Java 17
- Maven 3.9+
- Docker Desktop

## Puertos usados

- App Spring Boot: `8080`
- MySQL (contenedor): `3309`
- MongoDB (contenedor): `27018`

## Levantar contenedores

### MySQL

```bash
docker run -d --name mysql-academia \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=academia \
  -e MYSQL_USER=alumno \
  -e MYSQL_PASSWORD=alumno123 \
  -p 3309:3306 \
  mysql:8.0
```

### MongoDB

```bash
docker run -d --name mongo-academia \
  -e MONGO_INITDB_ROOT_USERNAME=root \
  -e MONGO_INITDB_ROOT_PASSWORD=root123 \
  -p 27018:27017 \
  mongo:7
```

## Tabla requerida

La aplicacion crea la tabla automaticamente al iniciar (archivo `src/main/resources/schema.sql`), gracias a:

- `spring.sql.init.mode=always`

Si quieres crearla manualmente:

```sql
CREATE TABLE IF NOT EXISTS estudiantes_procesados (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  grupo VARCHAR(20) NOT NULL,
  nota1 DOUBLE NOT NULL,
  nota2 DOUBLE NOT NULL,
  nota3 DOUBLE NOT NULL,
  promedio DOUBLE NOT NULL,
  PRIMARY KEY (id)
);
```

## Ejecutar proyecto

```bash
mvn spring-boot:run
```

## Endpoint base

- `http://localhost:8080/api/estudiantes/`
- `http://localhost:8080/api/reportes`
