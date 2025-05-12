package com.ssma.sgrh.controller;
// Define el paquete donde se encuentra esta clase.

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
// Importa las clases del modelo y servicio relacionadas con Turno.

@RestController
// Indica que esta clase es un controlador REST de Spring.
@RequestMapping("/api/turnos")
// Define la ruta base para todas las solicitudes HTTP que manejará este controlador.
public class TurnoController {

    @Autowired
    // Inyecta automáticamente una instancia de TurnoService en este controlador.
    private TurnoService turnoService;

    @GetMapping
    // Maneja solicitudes GET en la ruta base "/api/turnos".
    public List<Turno> obtenerTodos() {
        // Devuelve una lista de todos los turnos.
        return turnoService.obtenerTodos();
    }

    @GetMapping("/empleado/{empleadoId}")
    // Maneja solicitudes GET en "/api/turnos/empleado/{empleadoId}".
    public List<Turno> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        // Devuelve una lista de turnos asociados a un empleado específico.
        return turnoService.obtenerPorEmpleadoId(empleadoId);
    }

    @GetMapping("/{id}")
    // Maneja solicitudes GET en "/api/turnos/{id}".
    public Optional<Turno> obtenerPorId(@PathVariable Long id) {
        // Devuelve un turno específico por su ID.
        return turnoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    // Maneja solicitudes PUT en "/api/turnos/{id}".
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Turno turno) {
        // Actualiza un turno existente por su ID.
        try {
            return ResponseEntity.ok(turnoService.actualizar(id, turno));
            // Devuelve una respuesta HTTP 200 con el turno actualizado.
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            // Devuelve una respuesta HTTP 404 si no se encuentra el turno.
        }
    }

    @PostMapping
    // Maneja solicitudes POST en "/api/turnos".
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        // Guarda un nuevo turno en la base de datos.
        Turno nuevoTurno = turnoService.guardar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTurno);
        // Devuelve una respuesta HTTP 201 con el turno creado.
    }

    @DeleteMapping("/{id}")
    // Maneja solicitudes DELETE en "/api/turnos/{id}".
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        // Elimina un turno por su ID.
        try {
            turnoService.eliminar(id);
            return ResponseEntity.ok().build();
            // Devuelve una respuesta HTTP 200 si la eliminación fue exitosa.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el turno.");
            // Devuelve una respuesta HTTP 500 si ocurre un error al eliminar.
        }
    }
}
