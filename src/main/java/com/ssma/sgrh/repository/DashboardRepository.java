package com.ssma.sgrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Ausencia;
import com.ssma.sgrh.models.Empleado;
import com.ssma.sgrh.models.Nomina;

public class DashboardRepository {
    @Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {}

@Repository
public interface AusenciaRepository extends JpaRepository<Ausencia, Integer> {}

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Integer> {} // Si ya manejas nóminas

}
