package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Turno;
import com.ssma.sgrh.repository.TurnoRepository;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public List<Turno> obtenerTodos() {
        return turnoRepository.findAll();
    }

    public List<Turno> obtenerPorEmpleadoId(Long empleadoId) {
        return turnoRepository.findByEmpleado_Id(empleadoId);
    }

    public Optional<Turno> obtenerPorId(Long id) {
        return turnoRepository.findById(id);
    }

    public Turno guardar(Turno turno) {
        if (turno.getFecha() == null || turno.getHorarioInicio() == null || turno.getHorarioFin() == null) {
            throw new IllegalArgumentException("Error: Fecha, horario de inicio y horario de fin son obligatorios.");
        }

        if (turno.getEmpleado() == null || turno.getEmpleado().getId() == null) {
            throw new IllegalArgumentException("Error: El turno debe estar vinculado a un empleado.");
        }

        return turnoRepository.save(turno);
    }

    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }

    public Turno actualizar(Long id, Turno turno) {
        Optional<Turno> turnoExistente = turnoRepository.findById(id);

        if (turnoExistente.isPresent()) {
            Turno turnoActualizado = turnoExistente.get();
            turnoActualizado.setEmpleado(turno.getEmpleado());
            turnoActualizado.setFecha(turno.getFecha());
            turnoActualizado.setHorarioInicio(turno.getHorarioInicio());
            turnoActualizado.setHorarioFin(turno.getHorarioFin());

            return turnoRepository.save(turnoActualizado);
        } else {
            throw new RuntimeException("Error: Turno con ID " + id + " no encontrado.");
        }
    }
}
