package com.ssma.sgrh.controller;


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

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*") // Permitir peticiones desde tu frontend
public class DashboardController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private AusenciaRepository ausenciaRepository;

    @Autowired
    private NominaRepository nominaRepository; // si ya tienes esto

    @GetMapping
    public ResponseEntity<Map<String, Long>> getDashboardData() {
        Map<String, Long> response = new HashMap<>();

        long totalEmpleados = empleadoRepository.count();
        long totalAusencias = ausenciaRepository.count();
        long totalNominas = nominaRepository.count(); // si no tienes nominas aún, pon 0L

        response.put("empleados", totalEmpleados);
        response.put("ausencias", totalAusencias);
        response.put("nominas", totalNominas);

        return ResponseEntity.ok(response);
    }
}

