package com.academia.batch.proccesor;
import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.springframework.batch.item.ItemProcessor;
import org.slf4j.Logger;


public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

    //Proccesor que implemmenta ItemProcessor<Estudiante, EstudianteReporte>.
    // Convierte un Estudiante en un EstudianteReporte, copiando los campos nombre, grupo y promedio,
    // y calculando el estado como "APROBADO" si el promedio es >= 70, o "REPROBADO" si es menor.
    // Loguea "Step 2 - Reporte:{reporte}" y devuelve el reporte.

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReporteEstudianteProcessor.class);


    @Override
    public EstudianteReporte process(Estudiante estudiante) throws Exception {
        String estado = estudiante.getPromedio() >= 70 ? "APROBADO" : "REPROBADO";
        EstudianteReporte reporte = new EstudianteReporte();
        reporte.setNombre(estudiante.getNombre());
        reporte.setGrupo(estudiante.getGrupo());
        reporte.setPromedio(estudiante.getPromedio());
        reporte.setEstado(estado);
        log.info("Step 2 - Reporte: {}", reporte);
        return reporte;

    }
}
