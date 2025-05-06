package com.ssma.sgrh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssma.sgrh.models.SeguridadSocial;

@Repository
public interface SeguridadSocialRepository extends JpaRepository<SeguridadSocial, Long> {
}
