package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // ✅ Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    // ✅ Obtener un empleado por ID con validación
    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    // ✅ Guardar un empleado con validaciones
    public Empleado guardar(Empleado empleado) {
        if (empleado.getNombre() == null || empleado.getApellido() == null || empleado.getEmail() == null) {
            throw new IllegalArgumentException("Los campos nombre, apellido y email son obligatorios");
        }
        return empleadoRepository.save(empleado);
    }

    // ✅ Eliminar un empleado con verificación
    public void eliminar(Long id) {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(id);
        if (empleadoOpt.isPresent()) {
            empleadoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El empleado con ID " + id + " no existe.");
        }
    }
}

