package com.ssma.sgrh.controller;
// Define el paquete al que pertenece esta clase.

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssma.sgrh.models.Documento;
import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.repository.EmpleadoRepository;
import com.ssma.sgrh.service.DocumentoService;
// Importa las clases del modelo, repositorio y servicio utilizadas en esta clase.

@RestController
@RequestMapping("/api/documentos")
// Define esta clase como un controlador REST y establece el prefijo de las rutas como "/api/documentos".
public class DocumentoController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentoController.class);
    // Crea un logger para registrar mensajes de log.

    @Autowired
    private DocumentoService documentoService;
    // Inyecta el servicio de documentos para manejar la lógica de negocio relacionada con documentos.

    private EmpleadoRepository empleadoRepository;
    // Declara el repositorio de empleados para interactuar con la base de datos.

    // Obtener documentos por empleado
    @GetMapping("/empleado/{empleadoId}")
    // Mapea la ruta GET "/empleado/{empleadoId}" para obtener documentos de un empleado específico.
    public ResponseEntity<List<Documento>> obtenerDocumentosPorEmpleado(@PathVariable Long empleadoId) {
        // Define un método que recibe el ID del empleado como parámetro de la ruta.
        List<Documento> documentos = documentoService.obtenerDocumentosPorEmpleado(empleadoId);
        // Llama al servicio para obtener los documentos asociados al empleado.
        return ResponseEntity.ok(documentos);
        // Retorna la lista de documentos en la respuesta HTTP con estado 200 (OK).
    }

    @PostMapping
    // Mapea la ruta POST para subir un documento.
    public ResponseEntity<Documento> subirDocumento(
            @RequestParam("empleadoId") Long empleadoId,
            @RequestParam("nombreDocumento") String nombreDocumento,
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("archivo") MultipartFile archivo) {
        // Define un método para subir un documento con los parámetros necesarios.

        try {
            Optional<Empleado> empleadoOpt = empleadoRepository.findById(empleadoId);
            // Busca al empleado en la base de datos por su ID.
            if (!empleadoOpt.isPresent()) {
                // Si el empleado no existe, retorna un estado 400 (BAD REQUEST).
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Empleado empleado = empleadoOpt.get();
            // Obtiene el objeto empleado.

            String carpetaUploads = "uploads/";
            // Define la carpeta donde se guardarán los archivos.
            java.io.File directorio = new java.io.File(carpetaUploads);
            if (!directorio.exists()) {
                // Si la carpeta no existe, intenta crearla.
                if (!directorio.mkdirs()) {
                    // Si no se puede crear, retorna un estado 500 (INTERNAL SERVER ERROR).
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            }

            String rutaArchivo = carpetaUploads + archivo.getOriginalFilename();
            // Define la ruta completa del archivo.
            archivo.transferTo(new java.io.File(rutaArchivo));
            // Guarda el archivo en el sistema de archivos.

            Documento documento = new Documento();
            // Crea un nuevo objeto Documento.
            documento.setEmpleado(empleado);
            // Asocia el empleado al documento.
            documento.setNombreDocumento(nombreDocumento);
            // Establece el nombre del documento.
            documento.setTipoDocumento(tipoDocumento);
            // Establece el tipo del documento.
            documento.setArchivo(rutaArchivo);
            // Establece la ruta del archivo.

            Documento documentoGuardado = documentoService.guardarDocumento(documento);
            // Guarda el documento en la base de datos.
            return ResponseEntity.status(HttpStatus.CREATED).body(documentoGuardado);
            // Retorna el documento guardado con estado 201 (CREATED).

        } catch (Exception e) {
            logger.error("Error al subir el documento: ", e);
            // Registra un error en caso de excepción.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            // Retorna un estado 500 (INTERNAL SERVER ERROR).
        }
    }

    // Eliminar un documento
    @DeleteMapping("/{id}")
    // Mapea la ruta DELETE "/{id}" para eliminar un documento por su ID.
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        // Define un método para eliminar un documento.
        try {
            documentoService.eliminarDocumento(id);
            // Llama al servicio para eliminar el documento.
            return ResponseEntity.ok().build();
            // Retorna una respuesta vacía con estado 200 (OK).
        } catch (Exception e) {
            logger.error("Error al eliminar el documento: ", e);
            // Registra un error en caso de excepción.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el documento.");
            // Retorna un estado 500 (INTERNAL SERVER ERROR) con un mensaje de error.
        }
    }
}