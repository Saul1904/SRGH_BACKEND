package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.SeguridadSocial;
import com.ssma.sgrh.repository.SeguridadSocialRepository;

@Service
public class SeguridadSocialService {

    @Autowired
    private SeguridadSocialRepository seguridadSocialRepository;

    public Optional<SeguridadSocial> obtenerPorId(Long id) {
        return seguridadSocialRepository.findById(id);
    }

    public SeguridadSocial guardar(SeguridadSocial seguridadSocial) {
        return seguridadSocialRepository.save(seguridadSocial);
    }

    public void eliminar(Long id) {
        seguridadSocialRepository.deleteById(id);
    }

    public List<SeguridadSocial> obtenerTodos() {
        return seguridadSocialRepository.findAll();
    }

    public SeguridadSocial actualizar(Long id, SeguridadSocial beneficioActualizado) {
        Optional<SeguridadSocial> beneficioExistente = seguridadSocialRepository.findById(id);
    
        if (beneficioExistente.isPresent()) {
            SeguridadSocial beneficio = beneficioExistente.get();
            beneficio.setNumeroSeguro(beneficioActualizado.getNumeroSeguro());
            beneficio.setTipoSeguro(beneficioActualizado.getTipoSeguro());
            beneficio.setFechaRegistro(beneficioActualizado.getFechaRegistro());
            beneficio.setEstado(beneficioActualizado.getEstado());
            beneficio.setEmpleado(beneficioActualizado.getEmpleado());
    
            return seguridadSocialRepository.save(beneficio);
        } else {
            throw new RuntimeException("Error: Beneficio con ID " + id + " no encontrado.");
        }
    }
}
