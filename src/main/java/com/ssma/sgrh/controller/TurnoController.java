package com.ssma.sgrh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssma.sgrh.models.Turno;
import com.ssma.sgrh.service.TurnoService;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public List<Turno> obtenerTodos() {
        return turnoService.obtenerTodos();
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Turno> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return turnoService.obtenerPorEmpleadoId(empleadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Turno> turnoOpt = turnoService.obtenerPorId(id);

        if (turnoOpt.isPresent()) {
            return ResponseEntity.ok(turnoOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Turno con ID " + id + " no encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Turno turno) {
        try {
            return ResponseEntity.ok(turnoService.actualizar(id, turno));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.guardar(turno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            turnoService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el turno.");
        }
    }
}
