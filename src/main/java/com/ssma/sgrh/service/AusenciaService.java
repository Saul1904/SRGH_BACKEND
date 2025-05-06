package com.ssma.sgrh.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Ausencia;
import com.ssma.sgrh.repository.AusenciaRepository;

@Service
public class AusenciaService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private AusenciaRepository ausenciaRepository;

    public AusenciaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ausencia> obtenerTodas() {
        return ausenciaRepository.findAll();
    }

    public List<Ausencia> obtenerPorEmpleadoId(Long empleadoId) {
        return ausenciaRepository.findByEmpleado_Id(empleadoId);
    }

    public Optional<Ausencia> obtenerPorId(Long id) {
        return ausenciaRepository.findById(id);
    }

    public Ausencia guardar(Ausencia ausencia) {
        if (ausencia.getFechaInicio() == null || ausencia.getFechaFin() == null || ausencia.getTipoAusencia() == null) {
            throw new IllegalArgumentException("Error: Fecha inicio, fecha fin y tipo de ausencia son obligatorios.");
        }

        if (ausencia.getEmpleado() == null) {
            throw new IllegalArgumentException("Error: La ausencia debe estar vinculada a un empleado.");
        }

        return ausenciaRepository.save(ausencia);
    }

    public void eliminar(Long id) {
        ausenciaRepository.deleteById(id);
    }

    public Ausencia actualizar(Long id, Ausencia ausencia) {
        Optional<Ausencia> ausenciaExistente = ausenciaRepository.findById(id);

        if (ausenciaExistente.isPresent()) {
            Ausencia ausenciaActualizada = ausenciaExistente.get();
            ausenciaActualizada.setEmpleado(ausencia.getEmpleado());
            ausenciaActualizada.setFechaInicio(ausencia.getFechaInicio());
            ausenciaActualizada.setFechaFin(ausencia.getFechaFin());
            ausenciaActualizada.setTipoAusencia(ausencia.getTipoAusencia());

            return ausenciaRepository.save(ausenciaActualizada);
        } else {
            throw new RuntimeException("Error: Ausencia con ID " + id + " no encontrada.");
        }
    }
    public List<Map<String, Object>> obtenerPorMes() {
        // Ejemplo de cómo podrías agrupar las ausencias por mes
        String query = "SELECT MONTH(fecha_inicio) AS mes, COUNT(*) AS total " +
                       "FROM ausencias " +
                       "GROUP BY MONTH(fecha_inicio)";
        //Ejectuta la consulta
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        return result;
    }
    
    
}

