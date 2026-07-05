package com.academia.batch.proccesor;

import com.academia.batch.model.Estudiante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EstudianteProccesorTest {

    @Test
    void process_debeCalcularPromedioCorrectamente() throws Exception {
        EstudianteProccesor processor = new EstudianteProccesor();
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Juan");
        estudiante.setGrupo("A");
        estudiante.setNota1(80.0);
        estudiante.setNota2(85.0);
        estudiante.setNota3(90.0);

        Estudiante resultado = processor.process(estudiante);

        assertNotNull(resultado);
        assertEquals(85.0, resultado.getPromedio(), 0.0001);
    }
}
