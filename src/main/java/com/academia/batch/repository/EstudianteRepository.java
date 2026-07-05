package com.academia.batch.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;
   
    public EstudianteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EstudianteEntity> findAll() {
        String sql = "SELECT id, nombre, grupo, nota1, nota2, nota3, promedio " +
                "FROM estudiantes_procesados";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapEstudiante(rs));
    }

    public Optional<EstudianteEntity> findById(Long id) {
        String sql = "SELECT id, nombre, grupo, nota1, nota2, nota3, promedio " +
                "FROM estudiantes_procesados WHERE id = ?";

        List<EstudianteEntity> result = jdbcTemplate.query(sql, (rs, rowNum) -> mapEstudiante(rs), id);
        return result.stream().findFirst();
    }

    public EstudianteEntity save(EstudianteEntity estudiante) {
        String sql = "INSERT INTO estudiantes_procesados " +
                "(nombre, grupo, nota1, nota2, nota3, promedio) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getGrupo());
            ps.setDouble(3, estudiante.getNota1());
            ps.setDouble(4, estudiante.getNota2());
            ps.setDouble(5, estudiante.getNota3());
            ps.setDouble(6, estudiante.getPromedio());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            estudiante.setId(key.longValue());
        }
        return estudiante;
    }

    public int replaceById(Long id, EstudianteEntity estudiante) {
        String sql = "UPDATE estudiantes_procesados SET nombre = ?, grupo = ?, nota1 = ?, nota2 = ?, nota3 = ?, promedio = ? " +
                "WHERE id = ?";

        return jdbcTemplate.update(sql,
                estudiante.getNombre(),
                estudiante.getGrupo(),
                estudiante.getNota1(),
                estudiante.getNota2(),
                estudiante.getNota3(),
                estudiante.getPromedio(),
                id);
    }

    public int updateGrupoById(Long id, String grupo) {
        String sql = "UPDATE estudiantes_procesados SET grupo = ? WHERE id = ?";
        return jdbcTemplate.update(sql, grupo, id);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM estudiantes_procesados WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<EstudianteEntity> findByGrupo(String grupo) {
        String sql = "SELECT id, nombre, grupo, nota1, nota2, nota3, promedio " +
                "FROM estudiantes_procesados WHERE grupo = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapEstudiante(rs), grupo);
    }

    private EstudianteEntity mapEstudiante(java.sql.ResultSet rs) throws java.sql.SQLException {
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setId(rs.getLong("id"));
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setGrupo(rs.getString("grupo"));
        estudiante.setNota1(rs.getDouble("nota1"));
        estudiante.setNota2(rs.getDouble("nota2"));
        estudiante.setNota3(rs.getDouble("nota3"));
        estudiante.setPromedio(rs.getDouble("promedio"));
        return estudiante;
    }
}

