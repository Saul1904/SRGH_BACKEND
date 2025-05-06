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

import com.ssma.sgrh.models.Informe;
import com.ssma.sgrh.service.InformeService;

@RestController
@RequestMapping("/api/informes")
public class InformeController {

    @Autowired
    private InformeService informeService;

    @GetMapping
    public List<Informe> obtenerTodos() {
        return informeService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Informe> obtenerPorId(@PathVariable Long id) {
        return informeService.obtenerPorId(id);
    }

    @PostMapping
    public Informe guardar(@RequestBody Informe informe) {
        return informeService.guardar(informe);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        informeService.eliminar(id);
    }

    @PutMapping("/{id}")
public ResponseEntity<?> actualizarInforme(@PathVariable Long id, @RequestBody Informe informeActualizado) {
    Optional<Informe> informeOpt = informeService.obtenerPorId(id);
    
    if (informeOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("⚠️ Informe no encontrado.");
    }
    
    Informe informeExistente = informeOpt.get();
    informeExistente.setTipoInforme(informeActualizado.getTipoInforme());
    informeExistente.setFechaGeneracion(informeActualizado.getFechaGeneracion());

    return ResponseEntity.ok(informeService.guardar(informeExistente));
}
}
