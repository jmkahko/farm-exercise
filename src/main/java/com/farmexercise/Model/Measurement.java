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
public class Measurement extends AbstractPersistable<Long> {
    
    @Id
    @GeneratedValue(generator = "measurement_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "measurement_id_seq", sequenceName = "measurement_id_seq")
    private Long id;
    private String location;
    private String datetime;
    private String sensortype;
    private Double value;

    // Getterit
    public String getLocation() {
        return location;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getSensortype() {
        return sensortype;
    }

    public Double getValue() {
        return value;
    }

    // Setterit
    public void setLocation(String location) {
        this.location = location;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

/*
CREATE TABLE measurement (
    id SERIAL PRIMARY KEY,
    location VARCHAR(50) NOT NULL,
    datetime VARCHAR(24) NOT NULL,
	sensortype VARCHAR(11) NOT NULL,
    value DECIMAL NOT NULL
);
*/ 

// https://ntsim.uk/posts/how-to-use-hibernate-identifier-sequence-generators-properly
// CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
