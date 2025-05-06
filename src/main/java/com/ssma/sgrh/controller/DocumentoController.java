package com.ssma.sgrh.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssma.sgrh.models.Documento;
import com.ssma.sgrh.models.Documento.EstadoDocumento;
import com.ssma.sgrh.models.Documento.TipoDocumento;
import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.service.DocumentoService;
import com.ssma.sgrh.service.EmpleadoService;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*") // Solo si lo consumes desde frontend
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Documento> obtenerTodos() {
        return documentoService.obtenerTodos();
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Documento> obtenerPorEmpleado(@PathVariable Long empleadoId) {
        return documentoService.obtenerPorEmpleadoId(empleadoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Documento> doc = documentoService.obtenerPorId(id);
        return doc.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado."));
    }

    @GetMapping("/descargar/{id}")
    public ResponseEntity<byte[]> descargar(@PathVariable Long id) {
        Optional<Documento> documentoOpt = documentoService.obtenerPorId(id);
        if (documentoOpt.isEmpty()) return ResponseEntity.notFound().build();

        Documento documento = documentoOpt.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documento.getNombreDocumento() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(documento.getArchivo());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirDocumento(
            @RequestParam("id_empleado") Long idEmpleado,
            @RequestParam("nombre_documento") String nombre,
            @RequestParam("tipo_documento") String tipo,
            @RequestParam("archivo") MultipartFile archivo,
             @RequestParam("fechaSubida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSubida
            ) {

        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(idEmpleado);
        if (empleadoOpt.isEmpty()) return ResponseEntity.badRequest().body("Empleado no encontrado.");

        try {
            Documento documento = new Documento();
            documento.setEmpleado(empleadoOpt.get());
            documento.setNombreDocumento(nombre);
            documento.setTipoDocumento(TipoDocumento.valueOf(tipo.toUpperCase().replace(" ", "_")));
            documento.setFechaSubida(LocalDate.now());
            documento.setEstado(EstadoDocumento.VIGENTE);
            documento.setArchivo(archivo.getBytes());

            return ResponseEntity.ok(documentoService.guardar(documento));
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (documentoService.obtenerPorId(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado.");

        documentoService.eliminar(id);
        return ResponseEntity.ok("Documento eliminado.");
    }
}
