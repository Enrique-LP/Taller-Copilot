package com.academia.batch.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;
   
    public EstudianteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EstudianteEntity> findByGrupo(String grupo) {
        String sql = "SELECT id, nombre, grupo, nota1, nota2, nota3, promedio " +
                "FROM estudiantes_procesados WHERE grupo = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EstudianteEntity estudiante = new EstudianteEntity();
            estudiante.setId(rs.getLong("id"));
            estudiante.setNombre(rs.getString("nombre"));
            estudiante.setGrupo(rs.getString("grupo"));
            estudiante.setNota1(rs.getDouble("nota1"));
            estudiante.setNota2(rs.getDouble("nota2"));
            estudiante.setNota3(rs.getDouble("nota3"));
            estudiante.setPromedio(rs.getDouble("promedio"));
            return estudiante;
        }, grupo);
    }
}

