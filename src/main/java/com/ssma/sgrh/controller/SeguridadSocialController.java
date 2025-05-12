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

import com.ssma.sgrh.models.SeguridadSocial;
import com.ssma.sgrh.service.SeguridadSocialService;
// Importa las clases del modelo y servicio relacionadas con SeguridadSocial.

@RestController
@RequestMapping("/api/seguridad-social")
// Define esta clase como un controlador REST y establece la ruta base para las solicitudes.

public class SeguridadSocialController {
    // Define la clase del controlador para manejar las solicitudes relacionadas con SeguridadSocial.

    @Autowired
    private SeguridadSocialService seguridadSocialService;
    // Inyecta el servicio SeguridadSocialService para usarlo en este controlador.

    @GetMapping("/{id}")
    public Optional<SeguridadSocial> obtenerPorId(@PathVariable Long id) {
        // Maneja solicitudes GET para obtener un registro de SeguridadSocial por su ID.
        return seguridadSocialService.obtenerPorId(id);
        // Llama al servicio para obtener el registro correspondiente.
    }

    @GetMapping
    public List<SeguridadSocial> obtenerSeguridadSocial() {
        // Maneja solicitudes GET para obtener todos los registros de SeguridadSocial.
        return seguridadSocialService.obtenerTodos();
        // Llama al servicio para obtener la lista de todos los registros.
    }

    @PostMapping
    public SeguridadSocial guardar(@RequestBody SeguridadSocial seguridadSocial) {
        // Maneja solicitudes POST para guardar un nuevo registro de SeguridadSocial.
        return seguridadSocialService.guardar(seguridadSocial);
        // Llama al servicio para guardar el registro proporcionado.
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBeneficio(@PathVariable Long id, @RequestBody SeguridadSocial beneficioActualizado) {
        // Maneja solicitudes PUT para actualizar un registro de SeguridadSocial por su ID.
        try {
            return ResponseEntity.ok(seguridadSocialService.actualizar(id, beneficioActualizado));
            // Si la actualización es exitosa, devuelve una respuesta HTTP 200 con el registro actualizado.
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            // Si ocurre un error, devuelve una respuesta HTTP 404 con el mensaje de error.
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        // Maneja solicitudes DELETE para eliminar un registro de SeguridadSocial por su ID.
        seguridadSocialService.eliminar(id);
        // Llama al servicio para eliminar el registro correspondiente.
    }
}
