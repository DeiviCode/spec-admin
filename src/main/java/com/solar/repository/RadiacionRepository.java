package com.solar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.solar.model.Radiacion;

@Repository
public interface RadiacionRepository extends JpaRepository<Radiacion, Long> {
}
