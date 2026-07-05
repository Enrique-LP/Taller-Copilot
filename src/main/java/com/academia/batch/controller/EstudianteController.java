package com.academia.batch.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

	private final EstudianteRepository estudianteRepository;
	private final EstudianteService estudianteService;

	public EstudianteController(EstudianteRepository estudianteRepository, EstudianteService estudianteService) {
		this.estudianteRepository = estudianteRepository;
		this.estudianteService = estudianteService;
	}

	@GetMapping("/")
	public ResponseEntity<List<EstudianteEntity>> listarTodos() {
		return ResponseEntity.ok(estudianteRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstudianteEntity> obtenerPorId(@PathVariable Long id) {
		Optional<EstudianteEntity> estudiante = estudianteRepository.findById(id);
		return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/aprobados/total")
	public ResponseEntity<Map<String, Long>> totalAprobados() {
		Map<String, Long> response = new HashMap<>();
		response.put("totalAprobados", estudianteService.contarAprobados());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/")
	public ResponseEntity<EstudianteEntity> crear(@RequestBody EstudianteEntity estudiante) {
		EstudianteEntity creado = estudianteRepository.save(estudiante);
		URI location = URI.create("/api/estudiantes/" + creado.getId());
		return ResponseEntity.created(location).body(creado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstudianteEntity> reemplazar(@PathVariable Long id, @RequestBody EstudianteEntity estudiante) {
		if (estudianteRepository.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		estudiante.setId(id);
		estudianteRepository.replaceById(id, estudiante);
		return ResponseEntity.ok(estudiante);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EstudianteEntity> actualizarGrupo(@PathVariable Long id, @RequestBody Map<String, String> body) {
		String grupo = body.get("grupo");
		if (grupo == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<EstudianteEntity> existente = estudianteRepository.findById(id);
		if (existente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		estudianteRepository.updateGrupoById(id, grupo);
		EstudianteEntity actualizado = existente.get();
		actualizado.setGrupo(grupo);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		int deleted = estudianteRepository.deleteById(id);
		if (deleted == 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}
