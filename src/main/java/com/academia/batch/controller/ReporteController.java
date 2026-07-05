package com.academia.batch.controller;

import com.academia.batch.model.EstudianteReporte;
import com.academia.batch.repository.ReporteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;





// @RestController en /api/reportes que usa ReporteRepository:
// GET / lista todos los reportes; GET /estado/{estado} devuelve los que tengan ese estado
// (convertido a mayusculas) usando findByEstado.

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteRepository reporteRepository;

    public ReporteController(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @GetMapping
    public List<EstudianteReporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    @GetMapping("/estado/{estado}")
    public List<EstudianteReporte> getReportesByEstado(@PathVariable String estado) {
        return reporteRepository.findByEstado(estado.toUpperCase());
    }



    
    
}
