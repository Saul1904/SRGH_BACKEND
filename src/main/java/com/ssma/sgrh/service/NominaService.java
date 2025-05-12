package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Nomina;
import com.ssma.sgrh.repository.NominaRepository;
// Importa las clases del modelo y el repositorio relacionadas con la entidad Nómina.

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea gestionada como un componente de la aplicación.
public class NominaService {

    @Autowired
    // Inyecta automáticamente una instancia del repositorio de Nómina.
    private NominaRepository nominaRepository;

    public List<Nomina> obtenerTodas() {
        // Devuelve una lista con todas las nóminas almacenadas en la base de datos.
        return nominaRepository.findAll();
    }

    public List<Nomina> obtenerPorEmpleadoId(Long empleadoId) {
        // Devuelve una lista de nóminas asociadas a un empleado específico, usando su ID.
        return nominaRepository.findByEmpleadoId(empleadoId);
    }

    public Optional<Nomina> obtenerPorId(Long id) {
        // Busca una nómina específica por su ID y la devuelve como un valor opcional.
        return nominaRepository.findById(id);
    }

    public Nomina guardar(Nomina nomina) {
        // Guarda una nueva nómina o actualiza una existente en la base de datos.
        return nominaRepository.save(nomina);
    }

    public void eliminar(Long id) {
        // Elimina una nómina de la base de datos usando su ID.
        nominaRepository.deleteById(id);
    }

    public Nomina actualizar(Long id, Nomina nominaActualizada) {
        // Actualiza una nómina existente con nuevos valores.
        Optional<Nomina> nominaExistente = nominaRepository.findById(id);
        // Busca si la nómina con el ID proporcionado existe.

        if (nominaExistente.isPresent()) {
            // Si la nómina existe, actualiza sus valores.
            Nomina nomina = nominaExistente.get();
            nomina.setFechaPago(nominaActualizada.getFechaPago());
            // Actualiza la fecha de pago.
            nomina.setSueldoBase(nominaActualizada.getSueldoBase());
            // Actualiza el sueldo base.
            nomina.setDeducciones(nominaActualizada.getDeducciones());
            // Actualiza las deducciones.
            nomina.setPagoNeto(nominaActualizada.getPagoNeto());
            // Actualiza el pago neto.
            nomina.setEmpleado(nominaActualizada.getEmpleado());
            // Actualiza la referencia al empleado.

            return nominaRepository.save(nomina);
            // Guarda los cambios en la base de datos y devuelve la nómina actualizada.
        } else {
            // Si la nómina no existe, lanza una excepción.
            throw new RuntimeException("Error: Nómina con ID " + id + " no encontrada.");
        }
    }
}
