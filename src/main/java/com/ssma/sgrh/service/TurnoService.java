package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Turno;
import com.ssma.sgrh.repository.TurnoRepository;
// Importa las clases del modelo Turno y el repositorio TurnoRepository.

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor de Spring.
public class TurnoService {

    @Autowired
    // Inyecta automáticamente una instancia de TurnoRepository en esta clase.
    private TurnoRepository turnoRepository;

    public List<Turno> obtenerTodos() {
        // Método para obtener todos los turnos almacenados en la base de datos.
        return turnoRepository.findAll();
    }

    public List<Turno> obtenerPorEmpleadoId(Long empleadoId) {
        // Método para obtener todos los turnos asociados a un empleado específico por su ID.
        return turnoRepository.findByEmpleado_Id(empleadoId);
    }

    public Optional<Turno> obtenerPorId(Long id) {
        // Método para obtener un turno específico por su ID, envuelto en un Optional.
        return turnoRepository.findById(id);
    }

    public Turno guardar(Turno turno) {
        // Método para guardar un nuevo turno en la base de datos.
        return turnoRepository.save(turno);
    }

    public void eliminar(Long id) {
        // Método para eliminar un turno de la base de datos por su ID.
        turnoRepository.deleteById(id);
    }

    public Turno actualizar(Long id, Turno turno) {
        // Método para actualizar un turno existente en la base de datos.
        Optional<Turno> turnoExistente = turnoRepository.findById(id);
        // Busca el turno existente por su ID.

        if (turnoExistente.isPresent()) {
            // Si el turno existe, actualiza sus valores.
            Turno turnoActualizado = turnoExistente.get();
            turnoActualizado.setEmpleado(turno.getEmpleado());
            // Actualiza el empleado asociado al turno.
            turnoActualizado.setFecha(turno.getFecha());
            // Actualiza la fecha del turno.
            turnoActualizado.setHorarioInicio(turno.getHorarioInicio());
            // Actualiza el horario de inicio del turno.
            turnoActualizado.setHorarioFin(turno.getHorarioFin());
            // Actualiza el horario de fin del turno.

            return turnoRepository.save(turnoActualizado);
            // Guarda los cambios en la base de datos y devuelve el turno actualizado.
        } else {
            // Si el turno no existe, lanza una excepción.
            throw new RuntimeException("Error: Turno con ID " + id + " no encontrado.");
        }
    }
}
