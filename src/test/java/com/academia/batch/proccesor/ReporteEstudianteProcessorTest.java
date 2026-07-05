package com.academia.batch.proccesor;

import com.academia.batch.model.EstudianteReporte;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReporteEstudianteProcessorTest {

    @Test
    void process_debeMarcarAprobado_cuandoPromedioEs70() throws Exception {
        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        EstudianteReporte reporte = new EstudianteReporte();
        reporte.setNombre("Ana");
        reporte.setGrupo("B");
        reporte.setPromedio(70.0);

        EstudianteReporte resultado = processor.process(reporte);

        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstado());
    }

    @Test
    void process_debeMarcarReprobado_cuandoPromedioEs69_9() throws Exception {
        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        EstudianteReporte reporte = new EstudianteReporte();
        reporte.setNombre("Luis");
        reporte.setGrupo("C");
        reporte.setPromedio(69.9);

        EstudianteReporte resultado = processor.process(reporte);

        assertNotNull(resultado);
        assertEquals("REPROBADO", resultado.getEstado());
    }
}
