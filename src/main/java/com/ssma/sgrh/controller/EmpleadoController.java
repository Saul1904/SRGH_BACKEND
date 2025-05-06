package com.ssma.sgrh.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.repository.EmpleadoRepository;
import com.ssma.sgrh.service.EmpleadoService;


@RestController
@RequestMapping("/api/empleados") // ✅ Ajustamos la ruta para ser más clara en la API
public class EmpleadoController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
    

    @Autowired
    private EmpleadoService empleadoService;
    private EmpleadoRepository empleadoRepository;

    // ✅ Agregar un nuevo empleado
    @PostMapping
    public ResponseEntity<?> guardarEmpleado(@RequestBody Empleado empleado) {
    if (empleado.getNombre() == null || empleado.getApellido() == null || empleado.getEmail() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Campos obligatorios faltantes.");
    }
    
    Empleado nuevoEmpleado = empleadoService.guardar(empleado);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
}
    

    // ✅ Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        return ResponseEntity.ok(empleados);
    }

    // ✅ Obtener un empleado por ID con manejo de errores
  @GetMapping("/{id}")
  public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
      Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
      
      if (empleadoOpt.isPresent()) {
          return ResponseEntity.ok(empleadoOpt.get());
      } else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: El empleado con ID " + id + " no fue encontrado.");
      }
  }

    // ✅ Actualizar un empleado existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Optional<Empleado> empleadoExistente = empleadoService.obtenerPorId(id);

        if (empleadoExistente.isPresent()) {
            Empleado actualizado = empleadoExistente.get();
            actualizado.setNombre(empleado.getNombre());
            actualizado.setApellido(empleado.getApellido());
            actualizado.setEmail(empleado.getEmail());
            actualizado.setTelefono(empleado.getTelefono());
            actualizado.setSueldo(empleado.getSueldo());
            actualizado.setFechaContratacion(empleado.getFechaContratacion());
            actualizado.setDepartamento(empleado.getDepartamento());

            empleadoService.guardar(actualizado);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }

    // ✅ Eliminar un empleado con manejo de errores
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
        if (empleadoOpt.isPresent()) {
            empleadoService.eliminar(id);
            return ResponseEntity.ok("Empleado eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
    }

    
    // ✅ Subir imagen de empleado
    @PostMapping("/api/empleados/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id, @RequestParam("imagen") MultipartFile imagen) {
        try {
            // Usamos Optional y si no se encuentra, retornamos un error
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    
            if (imagen.isEmpty()) {
                return ResponseEntity.badRequest().body("Archivo vacío");
            }
    
            // Crear carpeta si no existe
            File carpeta = new File("uploads/imagenes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
    
            // Nombre de archivo único
            String nombreArchivo = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
            Path rutaArchivo = Paths.get("uploads/imagenes", nombreArchivo);
    
            // Guardar imagen
            imagen.transferTo(rutaArchivo.toFile());
    
            // Actualizar foto del empleado
            empleado.setFoto(nombreArchivo);
            empleadoRepository.save(empleado);
    
            return ResponseEntity.ok("Imagen subida y guardada con éxito");
    
        } catch (IOException e) {
            logger.error("Error al subir la imagen", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir imagen");
        }
    }
    

}

