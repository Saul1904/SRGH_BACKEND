package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Informe;
import com.ssma.sgrh.repository.InformeRepository;
// Importa las clases del modelo y repositorio relacionadas con "Informe".

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor de Spring.
public class InformeService {

    @Autowired
    // Inyecta automáticamente una instancia de InformeRepository en esta clase.
    private InformeRepository informeRepository;

    public List<Informe> obtenerTodos() {
        // Devuelve una lista con todos los informes almacenados en la base de datos.
        return informeRepository.findAll();
    }

    public Optional<Informe> obtenerPorId(Long id) {
        // Busca un informe por su ID y devuelve un Optional que puede contener el informe o estar vacío.
        return informeRepository.findById(id);
    }

    public Informe guardar(Informe informe) {
        // Guarda un informe en la base de datos y lo devuelve.
        return informeRepository.save(informe);
    }

    public void eliminar(Long id) {
        // Elimina un informe de la base de datos utilizando su ID.
        informeRepository.deleteById(id);
    }

    public long obtenerTotalInformes() {
        // Devuelve el número total de informes almacenados en la base de datos.
        return informeRepository.count();
    }

    public Informe obtenerUltimoInforme() {
        // Obtiene el último informe generado, ordenado por la fecha de generación en orden descendente.
        return informeRepository.findTopByOrderByFechaGeneracionDesc();
    }

    public List<Long> obtenerCantidadPorTipo() {
        // Devuelve una lista con la cantidad de informes agrupados por tipo específico.
        return List.of(
            informeRepository.contarPorTipo("Reporte Mensual"), // Cuenta los informes de tipo "Reporte Mensual".
            informeRepository.contarPorTipo("Evaluación de Desempeño"), // Cuenta los informes de tipo "Evaluación de Desempeño".
            informeRepository.contarPorTipo("Auditoría"), // Cuenta los informes de tipo "Auditoría".
            informeRepository.contarPorTipo("Otro") // Cuenta los informes de tipo "Otro".
        );
    }
}
