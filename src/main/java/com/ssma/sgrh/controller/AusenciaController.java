package com.ssma.sgrh.controller; // Paquete donde se encuentra la clase

import java.util.List;
import java.util.Map;
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

import com.ssma.sgrh.models.Ausencia; // Importación del modelo Ausencia
import com.ssma.sgrh.service.AusenciaService; // Importación del servicio AusenciaService

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/ausencias") // Define la ruta base para este controlador
public class AusenciaController {

    @Autowired // Inyección de dependencia del servicio AusenciaService
    private AusenciaService ausenciaService;

    @GetMapping // Mapeo para obtener todas las ausencias
    public List<Ausencia> obtenerTodas() {
        return ausenciaService.obtenerTodas(); // Llama al servicio para obtener todas las ausencias
    }

    @GetMapping("/empleado/{empleadoId}") // Mapeo para obtener ausencias por empleado
    public List<Ausencia> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return ausenciaService.obtenerPorEmpleadoId(empleadoId); // Llama al servicio para obtener ausencias por ID de empleado
    }

    @GetMapping("/{id}") // Mapeo para obtener una ausencia por su ID
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Ausencia> ausenciaOpt = ausenciaService.obtenerPorId(id); // Llama al servicio para obtener una ausencia por ID

        if (ausenciaOpt.isPresent()) { // Verifica si la ausencia existe
            return ResponseEntity.ok(ausenciaOpt.get()); // Retorna la ausencia encontrada
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Ausencia con ID " + id + " no encontrada."); // Retorna un error si no se encuentra
        }
    }

    @GetMapping("/por-mes") // Mapeo para obtener ausencias agrupadas por mes
    public List<Map<String, Object>> obtenerAusenciasPorMes() {
        return ausenciaService.obtenerPorMes(); // Llama al servicio para obtener ausencias por mes
    }

    @PutMapping("/{id}") // Mapeo para actualizar una ausencia
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ausencia ausencia) {
        try {
            return ResponseEntity.ok(ausenciaService.actualizar(id, ausencia)); // Llama al servicio para actualizar la ausencia
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Retorna un error si no se puede actualizar
        }
    }

    @PostMapping // Mapeo para guardar una nueva ausencia
    public ResponseEntity<Ausencia> guardar(@RequestBody Ausencia ausencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ausenciaService.guardar(ausencia)); // Llama al servicio para guardar la ausencia
    }

    @DeleteMapping("/{id}") // Mapeo para eliminar una ausencia
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ausenciaService.eliminar(id); // Llama al servicio para eliminar la ausencia
            return ResponseEntity.ok().build(); // Retorna una respuesta exitosa
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ausencia."); // Retorna un error si no se puede eliminar
        }
    }

}
