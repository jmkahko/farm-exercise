package com.farmexercise.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Measurement extends AbstractPersistable<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String location;
    public String datetime;
    public String sensortype;
    public Double value;
}

/*
CREATE TABLE measurement (
    id SERIAL PRIMARY KEY,
    location VARCHAR(50) NOT NULL,
    datetime TIMESTAMP NOT NULL,
	sensortype VARCHAR(10) NOT NULL,
    value DECIMAL(3,2) NOT NULL
);
*/