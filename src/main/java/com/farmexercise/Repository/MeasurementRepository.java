package com.farmexercise.Repository;

import java.time.Instant;
import java.util.List;

import com.farmexercise.Model.Measurement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByLocationAndSensortype(String location, String sensortype);
    List<Measurement> findByDatetimeBetweenAndLocation(String alkuPaiva, String loppuPaiva, String location);
    List<Measurement> findByDatetimeBetweenAndLocationAndSensortype(Instant alkuPaiva, Instant loppuPaiva, String location, String sensortype);

}
