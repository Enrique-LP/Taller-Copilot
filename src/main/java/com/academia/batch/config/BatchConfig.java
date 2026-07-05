package com.academia.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.proccesor.EstudianteProccesor;
import com.academia.batch.proccesor.ReporteEstudianteProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public FlatFileItemReader<Estudiante> estudianteReader() {
        return new FlatFileItemReaderBuilder<Estudiante>()
                .name("estudianteReader")
                .resource(new ClassPathResource("estudiantes.csv"))
                .linesToSkip(1)
                .delimited()
                .names("nombre", "grupo", "nota1", "nota2", "nota3")
                .targetType(Estudiante.class)
                .build();
    }

    @Bean
    public EstudianteProccesor estudianteProcessor() {
        return new EstudianteProccesor();
    }

    @Bean
    public JdbcBatchItemWriter<Estudiante> estudianteWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Estudiante>()
                .dataSource(dataSource)
                .sql("INSERT INTO estudiantes_procesados " +
                        "(nombre, grupo, nota1, nota2, nota3, promedio) " +
                        "VALUES (:nombre, :grupo, :nota1, :nota2, :nota3, :promedio)")
                .beanMapped()
                .build();
    }

    @Bean
    public Step paso1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Estudiante> estudianteReader,
            EstudianteProccesor estudianteProcessor,
            JdbcBatchItemWriter<Estudiante> estudianteWriter) {
        return new StepBuilder("paso1", jobRepository)
                .<Estudiante, Estudiante>chunk(3, transactionManager)
                .reader(estudianteReader)
                .processor(estudianteProcessor)
                .writer(estudianteWriter)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<EstudianteReporte> reporteReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<EstudianteReporte>()
                .name("reporteReader")
                .dataSource(dataSource)
                .sql("SELECT nombre, grupo, promedio FROM estudiantes_procesados")
                .rowMapper(new BeanPropertyRowMapper<>(EstudianteReporte.class))
                .build();
    }

    @Bean
    public ReporteEstudianteProcessor reporteEstudianteProccessor() {
        return new ReporteEstudianteProcessor();
    }

    @Bean
    public ItemWriter<EstudianteReporte> reporteWriter(MongoTemplate mongoTemplate) {
        return items -> mongoTemplate.insert(items, "reportes_estudiantes");
    }

    @Bean
    public Step paso2(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<EstudianteReporte> reporteReader,
            ReporteEstudianteProcessor reporteEstudianteProccessor,
            ItemWriter<EstudianteReporte> reporteWriter) {
        return new StepBuilder("paso2", jobRepository)
                .<EstudianteReporte, EstudianteReporte>chunk(3, transactionManager)
                .reader(reporteReader)
                .processor(reporteEstudianteProccessor)
                .writer(reporteWriter)
                .build();
    }

    @Bean
    public Job procesarCalificacionesJob(
            JobRepository jobRepository,
            Step paso1,
            Step paso2) {
        return new JobBuilder("procesarCalificacionesJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(paso1)
                .next(paso2)
                .build();
    }
}
