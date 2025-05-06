package com.ssma.sgrh.controller;

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

import com.ssma.sgrh.models.Ausencia;
import com.ssma.sgrh.service.AusenciaService;

@RestController
@RequestMapping("/api/ausencias")
public class AusenciaController {

    @Autowired
    private AusenciaService ausenciaService;

    @GetMapping
    public List<Ausencia> obtenerTodas() {
        return ausenciaService.obtenerTodas();
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Ausencia> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return ausenciaService.obtenerPorEmpleadoId(empleadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Ausencia> ausenciaOpt = ausenciaService.obtenerPorId(id);

        if (ausenciaOpt.isPresent()) {
            return ResponseEntity.ok(ausenciaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Ausencia con ID " + id + " no encontrada.");
        }
    }
    @GetMapping("/por-mes")
    public List<Map<String, Object>> obtenerAusenciasPorMes() {
    return ausenciaService.obtenerPorMes();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ausencia ausencia) {
        try {
            return ResponseEntity.ok(ausenciaService.actualizar(id, ausencia));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Ausencia> guardar(@RequestBody Ausencia ausencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ausenciaService.guardar(ausencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ausenciaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ausencia.");
        }
    }

    
}


