package com.ssma.sgrh.service;
// Define el paquete donde se encuentra esta clase.

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.SeguridadSocial;
import com.ssma.sgrh.repository.SeguridadSocialRepository;
// Importa las clases del modelo y repositorio relacionadas con SeguridadSocial.

@Service
// Marca esta clase como un servicio de Spring, lo que permite que sea detectada y gestionada por el contenedor de Spring.
public class SeguridadSocialService {

    @Autowired
    // Inyecta automáticamente una instancia del repositorio SeguridadSocialRepository.
    private SeguridadSocialRepository seguridadSocialRepository;

    // Método para obtener un registro de SeguridadSocial por su ID.
    public Optional<SeguridadSocial> obtenerPorId(Long id) {
        return seguridadSocialRepository.findById(id);
    }

    // Método para guardar un nuevo registro de SeguridadSocial en la base de datos.
    public SeguridadSocial guardar(SeguridadSocial seguridadSocial) {
        return seguridadSocialRepository.save(seguridadSocial);
    }

    // Método para eliminar un registro de SeguridadSocial por su ID.
    public void eliminar(Long id) {
        seguridadSocialRepository.deleteById(id);
    }

    // Método para obtener todos los registros de SeguridadSocial.
    public List<SeguridadSocial> obtenerTodos() {
        return seguridadSocialRepository.findAll();
    }

    // Método para actualizar un registro de SeguridadSocial existente.
    public SeguridadSocial actualizar(Long id, SeguridadSocial beneficioActualizado) {
        // Busca el registro existente por su ID.
        Optional<SeguridadSocial> beneficioExistente = seguridadSocialRepository.findById(id);
    
        if (beneficioExistente.isPresent()) {
            // Si el registro existe, actualiza sus campos con los valores proporcionados.
            SeguridadSocial beneficio = beneficioExistente.get();
            beneficio.setNumeroSeguro(beneficioActualizado.getNumeroSeguro());
            beneficio.setTipoSeguro(beneficioActualizado.getTipoSeguro());
            beneficio.setFechaRegistro(beneficioActualizado.getFechaRegistro());
            beneficio.setEstado(beneficioActualizado.getEstado());
            beneficio.setEmpleado(beneficioActualizado.getEmpleado());
    
            // Guarda los cambios en la base de datos y retorna el registro actualizado.
            return seguridadSocialRepository.save(beneficio);
        } else {
            // Si no se encuentra el registro, lanza una excepción con un mensaje de error.
            throw new RuntimeException("Error: Beneficio con ID " + id + " no encontrado.");
        }
    }
}
