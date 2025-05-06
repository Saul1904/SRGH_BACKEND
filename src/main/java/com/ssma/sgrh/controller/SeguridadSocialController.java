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

import com.ssma.sgrh.models.SeguridadSocial;
import com.ssma.sgrh.service.SeguridadSocialService;

@RestController
@RequestMapping("/api/seguridad-social")
public class SeguridadSocialController {

    @Autowired
    private SeguridadSocialService seguridadSocialService;

    @GetMapping("/{id}")
    public Optional<SeguridadSocial> obtenerPorId(@PathVariable Long id) {
        return seguridadSocialService.obtenerPorId(id);
    }

    @GetMapping
    public List<SeguridadSocial> obtenerSeguridadSocial() {
    return seguridadSocialService.obtenerTodos();
    }

    @PostMapping
    public SeguridadSocial guardar(@RequestBody SeguridadSocial seguridadSocial) {
        return seguridadSocialService.guardar(seguridadSocial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBeneficio(@PathVariable Long id, @RequestBody SeguridadSocial beneficioActualizado) {
    try {
        return ResponseEntity.ok(seguridadSocialService.actualizar(id, beneficioActualizado));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        seguridadSocialService.eliminar(id);
    }
}
