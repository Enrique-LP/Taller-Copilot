# PROMPTS EFICIENTES - TALLER COPILOT

## 1) Formula base de 5 piezas

Un prompt eficiente combina estas partes:

1. Contexto
2. Objetivo
3. Restricciones
4. Salida esperada
5. Validacion

Plantilla para reutilizar:

```text
Contexto:
[Proyecto, tecnologia, version, estilo del codigo]

Objetivo:
[Que quieres que genere o corrija, en terminos concretos]

Restricciones:
[Reglas tecnicas: versiones, patrones, sin X, con Y]

Salida esperada:
[Formato: archivo, metodo, endpoint, test, etc.]

Validacion:
[Como comprobar que esta bien: casos, codigos HTTP, asserts, compilacion]
```

---

## 2) Ejemplo rapido: malo vs bueno

Malo:

```text
haz un metodo de reprobados
```

Bueno:

```text
Contexto: En EstudianteService de un proyecto Spring Boot 3.2 con Java 17.
Objetivo: Genera un metodo contarReprobados().
Restricciones: Usa estudianteRepository.findAll() y Stream API (filter + count), sin bucles for.
Salida esperada: Metodo public long contarReprobados().
Validacion: Debe contar solo estudiantes con promedio < 70.
```

---

## 3) Prompts mejorados para este proyecto

### 3.1 Generar pom.xml

```text
Contexto: Proyecto Maven Spring Boot para procesamiento batch de calificaciones.
Objetivo: Genera un pom.xml para Spring Boot 3.2.2 y Java 17.
Restricciones:
- groupId: com.academia
- artifactId: spring-batch-final-calificaciones
- version: 1.0.0
- Dependencias: spring-boot-starter-batch, spring-boot-starter-web,
  spring-boot-starter-data-mongodb, mysql-connector-j (runtime),
  spring-boot-starter-jdbc, spring-boot-starter-test (test)
- Incluye spring-boot-maven-plugin
Salida esperada: Archivo pom.xml completo y valido.
Validacion: Debe compilar con mvn -DskipTests compile.
```

### 3.2 Explicar warning de dependencia en amarillo

```text
Contexto: VS Code marca en amarillo una dependencia de test en pom.xml.
Objetivo: Explica por que aparece y si es un problema real.
Restricciones: Responde en espanol, breve y accionable.
Salida esperada:
1) Causa tecnica
2) Impacto
3) Si se ignora o corrige
Validacion: Debe incluir al menos una accion concreta para verificar.
```

### 3.3 Analizar vulnerabilidades transitorias

```text
Contexto: Reporte de vulnerabilidades transitorias (Checkmarx) en un proyecto Spring Boot 3.2.2.
Objetivo: Prioriza y propone remediacion segura.
Restricciones:
- No romper compatibilidad de Spring Boot
- Indicar CVEs/GHSA criticos primero
- Proponer actualizaciones realistas por orden
Salida esperada:
1) Top riesgos por severidad
2) Plan de upgrade por fases
3) Cambios sugeridos en pom.xml
4) Comandos para validar
Validacion: Debe incluir como confirmar mitigacion con nuevo escaneo.
```

### 3.4 Explicar ItemProcessor y SLF4J

```text
Contexto: Clase EstudianteProccesor de Spring Batch.
Objetivo: Explica que hace SLF4J y que significa ItemProcessor<Estudiante, Estudiante>.
Restricciones: Explicacion para nivel junior, con ejemplo corto.
Salida esperada: Explicacion en bullets + mini ejemplo.
Validacion: Debe quedar claro que entra, que sale y donde se usa el log.
```

### 3.5 Explicar @Document y @Id en Mongo

```text
Contexto: Clase EstudianteReporte con @Document(collection = "reportes_estudiantes") y @Id.
Objetivo: Explicar mapeo a MongoDB.
Restricciones: Incluir relacion clase-coleccion y campo-id.
Salida esperada: Explicacion breve + ejemplo de documento JSON.
Validacion: Debe indicar como quedaria guardado en Mongo.
```

### 3.6 Generar BatchConfig

```text
Contexto: Proyecto Spring Boot 3.2 con Spring Batch 5, MySQL y MongoDB.
Objetivo: Genera BatchConfig con 2 steps y 1 job.
Restricciones:
- Step "paso1":
  - FlatFileItemReader de estudiantes.csv en classpath
  - Delimited con columnas: nombre, grupo, nota1, nota2, nota3
  - linesToSkip(1)
  - targetType Estudiante
  - Processor: EstudianteProccesor
  - Writer: JdbcBatchItemWriter (INSERT en estudiantes_procesados, beanMapped)
  - chunk(3)
- Step "paso2":
  - JdbcCursorItemReader con SELECT nombre, grupo, promedio FROM estudiantes_procesados
  - Processor: ReporteEstudianteProcessor
  - Writer: MongoItemWriter a reportes_estudiantes
  - chunk(3)
- Job "procesarCalificacionesJob" con RunIdIncrementer, paso1 -> paso2
- API de Spring Batch 5: JobBuilder/StepBuilder con JobRepository
Salida esperada: Clase BatchConfig completa y compilable.
Validacion: Debe compilar y crear beans sin errores de tipos genericos.
```

### 3.7 Generar application.properties

```text
Contexto: Spring Boot con MySQL y Mongo.
Objetivo: Crear application.properties.
Restricciones:
- datasource.url: jdbc:mysql://localhost:3309/academia
- datasource.username: alumno
- datasource.password: alumno123
- spring.batch.jdbc.initialize-schema=always
- spring.sql.init.mode=always
- mongodb.uri: mongodb://root:root123@localhost:27018/academia?authSource=admin
Salida esperada: Archivo properties exacto.
Validacion: La app debe conectar a MySQL y Mongo sin error de credenciales.
```

### 3.8 Generar EstudianteController

```text
Contexto: API REST de estudiantes en Spring Boot.
Objetivo: Crear @RestController en /api/estudiantes.
Restricciones:
- Constructor injection de EstudianteRepository y EstudianteService
- GET / -> listar todos
- GET /{id} -> 200 o 404
- GET /aprobados/total -> Map con totalAprobados
- POST / -> 201 Created
- PUT /{id} -> 200 o 404
- PATCH /{id} -> actualiza solo grupo con Map, 200 o 404
- DELETE /{id} -> 204 o 404
- Usar ResponseEntity en todos
Salida esperada: Clase completa con imports correctos.
Validacion: Endpoints deben responder codigos HTTP esperados.
```

### 3.9 Test unitario EstudianteProccesor y ReporteEstudianteProcessor

```text
Contexto: Proyecto con JUnit 5.
Objetivo: Generar pruebas unitarias de processors.
Restricciones:
- EstudianteProccesor: validar promedio calculado
- ReporteEstudianteProcessor: validar APROBADO con 70.0 y REPROBADO con 69.9
- Usar assertEquals y tolerancia para doubles cuando aplique
Salida esperada: 2 clases de test en src/test/java/com/academia/batch/proccesor.
Validacion: Deben pasar con mvn -Dtest=*ProcessorTest test.
```

### 3.10 Test unitario EstudianteService con Mockito

```text
Contexto: EstudianteService depende de EstudianteRepository.
Objetivo: Crear test unitario de contarAprobados().
Restricciones:
- @ExtendWith(MockitoExtension.class)
- @Mock EstudianteRepository
- @InjectMocks EstudianteService
- when(findAll()) retorna 2 aprobados y 1 reprobado
Salida esperada: Test que afirme resultado 2.
Validacion: Debe pasar con mvn -Dtest=EstudianteServiceTest test.
```

---

