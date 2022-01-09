package com.farmexercise.Repository;

import com.farmexercise.Model.Farm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    Farm findByFarmi(String farmi);
}
