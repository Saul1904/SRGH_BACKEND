package com.ssma.sgrh.controller;

// Define el paquete donde se encuentra esta clase.

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssma.sgrh.repository.AusenciaRepository;
import com.ssma.sgrh.repository.EmpleadoRepository;
import com.ssma.sgrh.repository.NominaRepository;

// Importa las interfaces de los repositorios que se usarán para acceder a la base de datos.

@RestController
@RequestMapping("/api/dashboard")
// Define esta clase como un controlador REST y asigna la ruta base "/api/dashboard" para sus endpoints.

@CrossOrigin(origins = "*")
// Permite solicitudes desde cualquier origen (útil para habilitar CORS en aplicaciones frontend).

public class DashboardController {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    // Inyecta el repositorio de empleados para acceder a los datos relacionados con empleados.

    @Autowired
    private AusenciaRepository ausenciaRepository;
    // Inyecta el repositorio de ausencias para acceder a los datos relacionados con ausencias.

    @Autowired
    private NominaRepository nominaRepository;
    // Inyecta el repositorio de nóminas para acceder a los datos relacionados con nóminas.

    @GetMapping
    // Define un endpoint HTTP GET que se ejecutará cuando se acceda a "/api/dashboard".

    public ResponseEntity<Map<String, Long>> getDashboardData() {
        // Define el método que manejará la solicitud GET y devolverá un ResponseEntity con un mapa de datos.

        Map<String, Long> response = new HashMap<>();
        // Crea un mapa para almacenar los datos que se enviarán como respuesta.

        long totalEmpleados = empleadoRepository.count();
        // Obtiene el número total de empleados desde la base de datos.

        long totalAusencias = ausenciaRepository.count();
        // Obtiene el número total de ausencias desde la base de datos.

        long totalNominas = nominaRepository.count();
        // Obtiene el número total de nóminas desde la base de datos.

        response.put("empleados", totalEmpleados);
        // Agrega el total de empleados al mapa de respuesta.

        response.put("ausencias", totalAusencias);
        // Agrega el total de ausencias al mapa de respuesta.

        response.put("nominas", totalNominas);
        // Agrega el total de nóminas al mapa de respuesta.

        return ResponseEntity.ok(response);
        // Devuelve una respuesta HTTP 200 (OK) con el mapa de datos como cuerpo de la respuesta.
    }
}
