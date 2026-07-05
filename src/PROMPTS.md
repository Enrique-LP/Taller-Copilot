# Evidencia de uso de GitHub Copilot - Enrique

## Prompt 1 - pom.xml
- **Prompt:**

  Contexto: Proyecto Maven Spring Boot para procesamiento batch de calificaciones.
  Objetivo: Genera un pom.xml para Spring Boot 3.2.2 y Java 17.
  Restricciones:
  - groupId com.academia
  - artifactId spring-batch-final-calificaciones
  - version 1.0.0
  - Dependencias: spring-boot-starter-batch, spring-boot-starter-web,
    spring-boot-starter-data-mongodb, mysql-connector-j (runtime),
    spring-boot-starter-jdbc, spring-boot-starter-test (test)
  - Incluye spring-boot-maven-plugin
  Salida esperada: Archivo pom.xml valido.
  Validacion: Debe compilar con `mvn -DskipTests compile`.

- **Modalidad:** Chat
- **Resultado:** aceptado y luego corregido. Se removio `spring-boot-starter-data-jpa` para evitar fallo de arranque por Hibernate Dialect cuando no se estaba usando JPA real en el flujo batch.

## Prompt 2 - Modelo Estudiante
- **Prompt:**

  Contexto: Proyecto Spring Batch con CSV -> MySQL.
  Objetivo: Genera el modelo `Estudiante` con campos `nombre`, `grupo`, `nota1`, `nota2`, `nota3`, `promedio`.
  Restricciones: incluir constructor vacio y getters/setters.
  Salida esperada: Clase simple tipo POJO.
  Validacion: Debe mapear correctamente con `FlatFileItemReader.targetType(Estudiante.class)`.

- **Modalidad:** Autocompletado
- **Resultado:** aceptado.

## Prompt 3 - ReporteEstudianteProcessor (umbral)
- **Prompt:**

  Contexto: Step 2 del job convierte datos a reporte para Mongo.
  Objetivo: En `ReporteEstudianteProcessor`, asigna estado segun promedio.
  Restricciones:
  - `APROBADO` si promedio `>= 70`
  - `REPROBADO` si promedio `< 70`
  Salida esperada: metodo `process` que retorna `EstudianteReporte`.
  Validacion: Debe pasar casos limite de 70.0 y 69.9.

- **Modalidad:** Chat
- **Resultado:** corregido.
  - Se ajustaron genericos del processor para coincidir con Step 2 (`ItemProcessor<EstudianteReporte, EstudianteReporte>`).
  - Se valido comportamiento con pruebas unitarias.

## Prompt 4 - /tests del Processor
- **Prompt:**

  Contexto: Proyecto con JUnit 5.
  Objetivo: Genera pruebas unitarias para `EstudianteProccesor` y `ReporteEstudianteProcessor`.
  Restricciones:
  - Verificar calculo de promedio en `EstudianteProccesor`
  - Verificar `APROBADO` con 70.0 y `REPROBADO` con 69.9
  - Usar `assertEquals` y tolerancia decimal cuando aplique
  Salida esperada: clases de test en `src/test/java/com/academia/batch/proccesor`.
  Validacion: pruebas en verde con `mvn test`.

- **Modalidad:** Chat (`/tests`)
- **Resultado:** aceptado.
  - Se crearon `EstudianteProccesorTest` y `ReporteEstudianteProcessorTest`.
  - Las pruebas pasaron en la ejecucion.

## Prompt 5 - Prompt malo vs. bueno (Bloque 3)
- **Malo:** `haz un metodo de reprobados`
- **Bueno:**

  Contexto: En `EstudianteService` de un proyecto Spring Boot 3.2 con Java 17.
  Objetivo: Genera `contarReprobados()`.
  Restricciones:
  - usar `estudianteRepository.findAll()`
  - usar Stream API (`filter` + `count`)
  - sin bucles `for`
  Salida esperada: metodo `public long contarReprobados()` con estilo similar a `contarAprobados()`.
  Validacion: Debe contar solo estudiantes con promedio `< 70`.

- **Diferencia observada:**
  - El malo da respuestas ambiguas.
  - El bueno produjo exactamente el metodo requerido y con el estilo esperado del servicio.

## Prompt 6 - Refactor sin romper
- **Prompt:**

  Contexto: Capa API REST de estudiantes con repositorio JDBC.
  Objetivo: Refactorizar/implementar controlador y repositorio sin romper endpoints.
  Restricciones:
  - usar constructor injection
  - `ResponseEntity` para codigos HTTP
  - mantener contrato: GET, POST, PUT, PATCH, DELETE
  - no introducir cambios destructivos en otras capas
  Salida esperada: clases compilables con rutas funcionales.
  Validacion:
  - tests unitarios en verde
  - endpoints respondiendo codigos esperados (200, 201, 204, 404).

- **Modalidad:** Chat
- **¿Tests siguieron en verde?:** si.
  - Se agregaron metodos faltantes en repositorio (`findAll`, `findById`, `save`, `replaceById`, `updateGrupoById`, `deleteById`) y se mantuvo funcional la logica del servicio.
