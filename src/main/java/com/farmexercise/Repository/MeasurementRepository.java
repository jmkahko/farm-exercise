package com.farmexercise.Repository;

import com.farmexercise.Model.Measurement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    
}
