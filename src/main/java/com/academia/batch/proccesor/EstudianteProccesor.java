package com.academia.batch.proccesor;

import com.academia.batch.model.Estudiante;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.Logger;


// Proccessor de Spring Batch que implementa ItemProcessor<Estudiante, Estudiante>
public class EstudianteProccesor implements ItemProcessor<Estudiante, Estudiante> {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EstudianteProccesor.class);

    // En el método ptoccess: calcula el promedio como (nota1 + nota2 + nota3) / 3,
    // y lo asigna al campo promedio del estudiante con setPromedio, registra un log con SLF4J
    // "Step 1 - Procesndo: {estudiante}" y devuelve el estudiante.

    @Override
    public Estudiante process(Estudiante estudiante) throws Exception {
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3;
        estudiante.setPromedio(promedio);
        log.info("Step 1 - Procesando: {}", estudiante);
        return estudiante;
    }







}
