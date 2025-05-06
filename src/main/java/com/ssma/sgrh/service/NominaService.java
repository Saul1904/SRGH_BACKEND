package com.ssma.sgrh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssma.sgrh.models.Nomina;
import com.ssma.sgrh.repository.NominaRepository;

@Service
public class NominaService {

    @Autowired
    private NominaRepository nominaRepository;

    public List<Nomina> obtenerTodas() {
        return nominaRepository.findAll();
    }

    public List<Nomina> obtenerPorEmpleadoId(Long empleadoId) {
        return nominaRepository.findByEmpleadoId(empleadoId);
    }

    public Optional<Nomina> obtenerPorId(Long id) {
        return nominaRepository.findById(id);
    }

    public Nomina guardar(Nomina nomina) {
        return nominaRepository.save(nomina);
    }

    public void eliminar(Long id) {
        nominaRepository.deleteById(id);
    }

    public Nomina actualizar(Long id, Nomina nominaActualizada) {
        Optional<Nomina> nominaExistente = nominaRepository.findById(id);
    
        if (nominaExistente.isPresent()) {
            Nomina nomina = nominaExistente.get();
            nomina.setFechaPago(nominaActualizada.getFechaPago());
            nomina.setSueldoBase(nominaActualizada.getSueldoBase());
            nomina.setDeducciones(nominaActualizada.getDeducciones());
            nomina.setPagoNeto(nominaActualizada.getPagoNeto());
            nomina.setEmpleado(nominaActualizada.getEmpleado());
    
            return nominaRepository.save(nomina);
        } else {
            throw new RuntimeException("Error: Nómina con ID " + id + " no encontrada.");
        }
    }
}
