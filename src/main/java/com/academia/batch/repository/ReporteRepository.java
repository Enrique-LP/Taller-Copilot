//Interfaz ReporteRepository que extiende MongoRepository<ReporteEntity, String>
//con un método findByGrupo(String grupo) que devuelve List<ReporteEntity>
package com.academia.batch.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.academia.batch.model.EstudianteReporte;



public interface ReporteRepository extends MongoRepository<EstudianteReporte, String> {
    List<EstudianteReporte> findByGrupo(String grupo);

    List<EstudianteReporte> findByEstado(String estado);
}




