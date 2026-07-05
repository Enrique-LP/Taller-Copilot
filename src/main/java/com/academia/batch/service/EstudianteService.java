package com.academia.batch.service;


// @Service con inyeccion por constructor de EstudianteRepository
// Metodo contarAprobados() que devuelve el numero de estudiantes con promedio >=70,
// usando findAll() y un stream con filter y count
import com.academia.batch.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public long contarAprobados() {
        return estudianteRepository.findAll().stream()
                .filter(estudiante -> estudiante.getPromedio() >= 70)
                .count();
    }

}
