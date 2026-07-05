package com.academia.batch.proccesor;
import com.academia.batch.model.EstudianteReporte;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.Logger;


public class ReporteEstudianteProcessor implements ItemProcessor<EstudianteReporte, EstudianteReporte> {

    //Proccesor que implemmenta ItemProcessor<EstudianteReporte, EstudianteReporte>.
    // Completa el estado del EstudianteReporte,
    // y calculando el estado como "APROBADO" si el promedio es >= 70, o "REPROBADO" si es menor.
    // Loguea "Step 2 - Reporte:{reporte}" y devuelve el reporte.

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReporteEstudianteProcessor.class);


    @Override
    public EstudianteReporte process(EstudianteReporte reporte) throws Exception {
        String estado = reporte.getPromedio() >= 70 ? "APROBADO" : "REPROBADO";
        reporte.setEstado(estado);
        log.info("Step 2 - Reporte: {}", reporte);
        return reporte;

    }

}
