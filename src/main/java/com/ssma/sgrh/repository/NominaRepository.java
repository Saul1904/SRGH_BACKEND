package com.ssma.sgrh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.Nomina;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {
    List<Nomina> findByEmpleadoId(Long empleadoId);
    @Query("SELECT COUNT(n) FROM Nomina n")
    int countNominas();

}
 