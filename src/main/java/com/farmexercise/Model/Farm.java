package com.farmexercise.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(generator = "farm_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "farm_id_seq", sequenceName = "farm_id_seq")
    private Long id;
    private String farmi;

    // Getterit
    public Long getId() {
        return id;
    }

    public String getFarmi() {
        return farmi;
    }

    // Setterit
    public void setId(Long id) {
        this.id = id;
    }

    public void setFarmi(String farmi) {
        this.farmi = farmi;
    }
}
