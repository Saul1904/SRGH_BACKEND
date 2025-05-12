package com.ssma.sgrh.service;

// Importa las clases necesarias para trabajar con listas, mapas, y valores opcionales
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Ausencia;
import com.ssma.sgrh.repository.AusenciaRepository;

// Marca esta clase como un servicio de Spring
@Service
public class AusenciaService {
    // JdbcTemplate se utiliza para ejecutar consultas SQL directamente
    private final JdbcTemplate jdbcTemplate;

    // Inyección del repositorio de Ausencia
    @Autowired
    private AusenciaRepository ausenciaRepository;

    // Constructor para inicializar JdbcTemplate
    public AusenciaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para obtener todas las ausencias
    public List<Ausencia> obtenerTodas() {
        return ausenciaRepository.findAll(); // Llama al repositorio para obtener todas las ausencias
    }

    // Método para obtener ausencias por el ID del empleado
    public List<Ausencia> obtenerPorEmpleadoId(Long empleadoId) {
        // Busca las ausencias asociadas al empleado
        List<Ausencia> ausencias = ausenciaRepository.findByEmpleadoId(empleadoId);
        // Imprime en consola las ausencias encontradas
        System.out.println("📌 Ausencias encontradas para empleado " + empleadoId + ": " + ausencias);
        return ausencias; // Devuelve la lista de ausencias
    }

    // Método para obtener una ausencia específica por su ID
    public Optional<Ausencia> obtenerPorId(Long id) {
        return ausenciaRepository.findById(id); // Busca la ausencia por ID
    }

    // Método para guardar una nueva ausencia
    public Ausencia guardar(Ausencia ausencia) {
        // Valida que las fechas y el tipo de ausencia no sean nulos
        if (ausencia.getFechaInicio() == null || ausencia.getFechaFin() == null || ausencia.getTipoAusencia() == null) {
            throw new IllegalArgumentException("Error: Fecha inicio, fecha fin y tipo de ausencia son obligatorios.");
        }

        // Valida que la ausencia esté vinculada a un empleado
        if (ausencia.getEmpleado() == null) {
            throw new IllegalArgumentException("Error: La ausencia debe estar vinculada a un empleado.");
        }

        // Guarda la ausencia en la base de datos
        return ausenciaRepository.save(ausencia);
    }

    // Método para eliminar una ausencia por su ID
    public void eliminar(Long id) {
        ausenciaRepository.deleteById(id); // Elimina la ausencia del repositorio
    }

    // Método para actualizar una ausencia existente
    public Ausencia actualizar(Long id, Ausencia ausencia) {
        // Busca la ausencia existente por su ID
        Optional<Ausencia> ausenciaExistente = ausenciaRepository.findById(id);

        // Si la ausencia existe, actualiza sus valores
        if (ausenciaExistente.isPresent()) {
            Ausencia ausenciaActualizada = ausenciaExistente.get();
            ausenciaActualizada.setEmpleado(ausencia.getEmpleado());
            ausenciaActualizada.setFechaInicio(ausencia.getFechaInicio());
            ausenciaActualizada.setFechaFin(ausencia.getFechaFin());
            ausenciaActualizada.setTipoAusencia(ausencia.getTipoAusencia());

            // Guarda los cambios en la base de datos
            return ausenciaRepository.save(ausenciaActualizada);
        } else {
            // Si no se encuentra la ausencia, lanza una excepción
            throw new RuntimeException("Error: Ausencia con ID " + id + " no encontrada.");
        }
    }

    // Método para obtener un resumen de ausencias agrupadas por mes
    public List<Map<String, Object>> obtenerPorMes() {
        // Consulta SQL para agrupar las ausencias por mes y contar cuántas hay en cada mes
        String query = "SELECT MONTH(fecha_inicio) AS mes, COUNT(*) AS total " +
                       "FROM ausencias " +
                       "GROUP BY MONTH(fecha_inicio)";
        // Ejecuta la consulta y devuelve los resultados
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        return result;
    }
}
