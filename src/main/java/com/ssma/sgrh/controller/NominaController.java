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

import com.ssma.sgrh.models.Nomina;
import com.ssma.sgrh.service.NominaService;
// Importa las clases del modelo y servicio relacionadas con "Nomina".

@RestController
// Indica que esta clase es un controlador REST.

@RequestMapping("/api/nominas")
// Define la ruta base para todas las solicitudes HTTP de este controlador.

public class NominaController {
    // Define la clase del controlador para manejar las operaciones relacionadas con "Nomina".

    @Autowired
    private NominaService nominaService;
    // Inyecta el servicio de "Nomina" para usarlo en este controlador.

    @GetMapping
    public List<Nomina> obtenerTodas() {
        // Maneja solicitudes GET en "/api/nominas".
        return nominaService.obtenerTodas();
        // Devuelve una lista de todas las nóminas.
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Nomina> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        // Maneja solicitudes GET en "/api/nominas/empleado/{empleadoId}".
        return nominaService.obtenerPorEmpleadoId(empleadoId);
        // Devuelve una lista de nóminas asociadas a un empleado específico.
    }

    @GetMapping("/{id}")
    public Optional<Nomina> obtenerPorId(@PathVariable Long id) {
        // Maneja solicitudes GET en "/api/nominas/{id}".
        return nominaService.obtenerPorId(id);
        // Devuelve una nómina específica por su ID.
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Nomina nomina) {
        // Maneja solicitudes PUT en "/api/nominas/{id}" para actualizar una nómina.
        try {
            return ResponseEntity.ok(nominaService.actualizar(id, nomina));
            // Si la actualización es exitosa, devuelve un estado HTTP 200 con la nómina actualizada.
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            // Si ocurre un error, devuelve un estado HTTP 404 con el mensaje de error.
        }
    }

    @PostMapping
    public Nomina guardar(@RequestBody Nomina nomina) {
        // Maneja solicitudes POST en "/api/nominas" para guardar una nueva nómina.
        return nominaService.guardar(nomina);
        // Guarda la nómina y la devuelve.
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        // Maneja solicitudes DELETE en "/api/nominas/{id}" para eliminar una nómina.
        nominaService.eliminar(id);
        // Llama al servicio para eliminar la nómina por su ID.
    }
}
