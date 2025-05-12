package com.ssma.sgrh.controller;
// Define el paquete al que pertenece esta clase.

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
// Importa clases del modelo, repositorio y servicio relacionadas con "Empleado".

@RestController
@RequestMapping("/api/empleados")
// Define que esta clase es un controlador REST y establece la ruta base para las solicitudes.

public class EmpleadoController {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
    // Crea un logger para registrar información y errores.

    @Autowired
    private EmpleadoService empleadoService;
    // Inyecta el servicio de empleados para manejar la lógica de negocio.
   
    private EmpleadoRepository empleadoRepository;
    // Declara el repositorio de empleados (aunque no está inyectado correctamente).

    @Autowired
public EmpleadoController(EmpleadoService empleadoService, EmpleadoRepository empleadoRepository) {
    this.empleadoService = empleadoService;
    this.empleadoRepository = empleadoRepository;
}

    @Value("${upload.dir}")
    private String uploadDir;

    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Empleado> empleadoOpt = empleadoRepository.findById(id);
            if (!empleadoOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Empleado empleado = empleadoOpt.get();

            // Generar nombre único
            String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get(uploadDir + File.separator + nombreArchivo);
            Files.copy(file.getInputStream(), ruta);

            // Actualizar URL de la imagen
            empleado.setFoto("/uploads/" + nombreArchivo);
            empleadoRepository.save(empleado);

            return ResponseEntity.ok("Imagen subida correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarEmpleado(@RequestBody Empleado empleado) {
        // Define un endpoint POST para guardar un empleado.
        if (empleado.getNombre() == null || empleado.getApellido() == null || empleado.getEmail() == null) {
            // Valida que los campos obligatorios no sean nulos.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Campos obligatorios faltantes.");
        }
        Empleado nuevoEmpleado = empleadoService.guardar(empleado);
        // Guarda el empleado usando el servicio.
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        // Devuelve una respuesta con el empleado creado.
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerEmpleados() {
        // Define un endpoint GET para obtener todos los empleados.
        List<Empleado> empleados = empleadoService.obtenerTodos();
        // Obtiene la lista de empleados desde el servicio.
        return ResponseEntity.ok(empleados);
        // Devuelve la lista de empleados en la respuesta.
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        // Define un endpoint GET para obtener un empleado por su ID.
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
        // Busca el empleado por ID usando el servicio.
        if (empleadoOpt.isPresent()) {
            return ResponseEntity.ok(empleadoOpt.get());
            // Si se encuentra, devuelve el empleado.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: El empleado con ID " + id + " no fue encontrado.");
            // Si no se encuentra, devuelve un error 404.
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        // Define un endpoint PUT para actualizar un empleado existente.
        Optional<Empleado> empleadoExistente = empleadoService.obtenerPorId(id);
        // Busca el empleado por ID.
        if (empleadoExistente.isPresent()) {
            Empleado actualizado = empleadoExistente.get();
            // Si existe, actualiza solo los campos enviados.
            if (empleado.getNombre() != null) actualizado.setNombre(empleado.getNombre());
            if (empleado.getApellido() != null) actualizado.setApellido(empleado.getApellido());
            if (empleado.getEmail() != null) actualizado.setEmail(empleado.getEmail());
            if (empleado.getTelefono() != null) actualizado.setTelefono(empleado.getTelefono());
            if (empleado.getSueldo() != null) actualizado.setSueldo(empleado.getSueldo());
            if (empleado.getFechaContratacion() != null) actualizado.setFechaContratacion(empleado.getFechaContratacion());
            if (empleado.getDepartamento() != null) actualizado.setDepartamento(empleado.getDepartamento());
            empleadoService.guardar(actualizado);
            // Guarda los cambios en el servicio.
            return ResponseEntity.ok(actualizado);
            // Devuelve el empleado actualizado.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
            // Si no se encuentra, devuelve un error 404.
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        // Define un endpoint DELETE para eliminar un empleado.
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
        // Busca el empleado por ID.
        if (empleadoOpt.isPresent()) {
            empleadoService.eliminar(id);
            // Si existe, lo elimina usando el servicio.
            return ResponseEntity.ok("Empleado eliminado con éxito");
            // Devuelve un mensaje de éxito.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
            // Si no se encuentra, devuelve un error 404.
        }
    }
}
