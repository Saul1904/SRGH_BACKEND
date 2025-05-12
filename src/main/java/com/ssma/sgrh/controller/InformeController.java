package com.ssma.sgrh.controller;

// Importa las clases y anotaciones necesarias
import java.util.HashMap;
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

import com.ssma.sgrh.models.Informe;
import com.ssma.sgrh.repository.InformeRepository;
import com.ssma.sgrh.service.InformeService;

// Marca esta clase como un controlador REST para manejar solicitudes HTTP
@RestController
// Asocia todas las rutas de este controlador al prefijo "/api/informes"
@RequestMapping("/api/informes")
public class InformeController {

    // Inyecta la dependencia del servicio InformeService
    @Autowired
    private InformeService informeService;
    // Inyecta la dependencia del repositorio InformeRepository
    @Autowired
    private InformeRepository informeRepository;

    // Maneja solicitudes GET para obtener todos los registros de "Informe"
    @GetMapping
    public List<Informe> obtenerTodos() {
        return informeService.obtenerTodos(); // Llama al servicio para obtener todos los registros
    }

    // Maneja solicitudes GET para obtener un "Informe" específico por su ID
    @GetMapping("/{id}")
    public Optional<Informe> obtenerPorId(@PathVariable Long id) {
        return informeService.obtenerPorId(id); // Llama al servicio para obtener un registro por ID
    }
     
    // Maneja solicitudes GET para proporcionar un resumen de los datos de "Informe"
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> getResumenInformes() {
        Map<String, Object> response = new HashMap<>(); // Crea un mapa de respuesta

        // Obtiene el número total de registros de "Informe"
        long totalInformes = informeRepository.count();

        // Obtiene el "Informe" más reciente generado
        Informe ultimoInforme = informeRepository.findTopByOrderByFechaGeneracionDesc();

        // Agrega el total y los detalles del último "Informe" al mapa de respuesta
        response.put("totalInformes", totalInformes);
        response.put("ultimoInforme", ultimoInforme != null ? Map.of(
            "tipoInforme", ultimoInforme.getTipoInforme(),
            "fechaGeneracion", ultimoInforme.getFechaGeneracion()
        ) : null);

        return ResponseEntity.ok(response); // Devuelve la respuesta con un HTTP 200 OK
    }

    // Maneja solicitudes GET para proporcionar datos para una gráfica
    @GetMapping("/grafica")
    public ResponseEntity<Map<String, Object>> obtenerDatosGrafica() {
        Map<String, Object> datos = new HashMap<>(); // Crea un mapa de respuesta
        datos.put("labels", List.of("Reporte Mensual", "Evaluación de Desempeño", "Auditoría", "Otro")); // Etiquetas de la gráfica
        datos.put("data", informeService.obtenerCantidadPorTipo()); // Datos de la gráfica desde el servicio
        return ResponseEntity.ok(datos); // Devuelve la respuesta con un HTTP 200 OK
    }

    // Maneja solicitudes POST para guardar un nuevo "Informe"
    @PostMapping
    public Informe guardar(@RequestBody Informe informe) {
        return informeService.guardar(informe); // Llama al servicio para guardar el registro
    }

    // Maneja solicitudes DELETE para eliminar un "Informe" por su ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        informeService.eliminar(id); // Llama al servicio para eliminar el registro
    }

    // Maneja solicitudes PUT para actualizar un "Informe" existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInforme(@PathVariable Long id, @RequestBody Informe informeActualizado) {
        // Obtiene el "Informe" existente por su ID
        Optional<Informe> informeOpt = informeService.obtenerPorId(id);
        
        // Si el "Informe" no existe, devuelve una respuesta 404 Not Found
        if (informeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("⚠️ Informe no encontrado.");
        }
        
        // Actualiza el "Informe" existente con los nuevos datos
        Informe informeExistente = informeOpt.get();
        informeExistente.setTipoInforme(informeActualizado.getTipoInforme());
        informeExistente.setFechaGeneracion(informeActualizado.getFechaGeneracion());

        // Guarda el "Informe" actualizado y lo devuelve en la respuesta
        return ResponseEntity.ok(informeService.guardar(informeExistente));
    }
}
