package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.repository.EmpleadoRepository;
// Importa las clases del modelo y repositorio relacionadas con "Empleado".

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor de Spring.
public class EmpleadoService {

    @Autowired
    // Inyecta automáticamente una instancia del repositorio de empleados.
    private EmpleadoRepository empleadoRepository;

    // ✅ Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        // Devuelve una lista con todos los empleados almacenados en la base de datos.
        return empleadoRepository.findAll();
    }

    // ✅ Obtener un empleado por ID con validación
    public Optional<Empleado> obtenerPorId(Long id) {
        // Busca un empleado por su ID y devuelve un Optional que puede contener el empleado o estar vacío.
        return empleadoRepository.findById(id);
    }

    // ✅ Guardar un empleado con validaciones
    public Empleado guardar(Empleado empleado) {
        // Guarda un empleado en la base de datos y devuelve el empleado guardado.
        return empleadoRepository.save(empleado);
    }

    // ✅ Eliminar un empleado con verificación
    public void eliminar(Long id) {
        // Busca un empleado por su ID para verificar si existe.
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(id);
        if (empleadoOpt.isPresent()) {
            // Si el empleado existe, lo elimina de la base de datos.
            empleadoRepository.deleteById(id);
        } else {
            // Si el empleado no existe, lanza una excepción indicando que no se encontró.
            throw new IllegalArgumentException("El empleado con ID " + id + " no existe.");
        }
    }
}
