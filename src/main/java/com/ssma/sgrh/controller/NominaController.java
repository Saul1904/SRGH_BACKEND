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

import com.ssma.sgrh.models.Nomina;
import com.ssma.sgrh.service.NominaService;

@RestController
@RequestMapping("/api/nominas")
public class NominaController {

    @Autowired
    private NominaService nominaService;

    @GetMapping
    public List<Nomina> obtenerTodas() {
        return nominaService.obtenerTodas();
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Nomina> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return nominaService.obtenerPorEmpleadoId(empleadoId);
    }

    @GetMapping("/{id}")
    public Optional<Nomina> obtenerPorId(@PathVariable Long id) {
        return nominaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Nomina nomina) {
    try {
        return ResponseEntity.ok(nominaService.actualizar(id, nomina));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }

    @PostMapping
    public Nomina guardar(@RequestBody Nomina nomina) {
        return nominaService.guardar(nomina);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        nominaService.eliminar(id);
    }
}
