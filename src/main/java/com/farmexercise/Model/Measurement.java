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

    private Long fileobjectid;
    private String location;
    private String datetime;
    private String sensortype;
    private Double value;

    // Getterit
    public Long getFileobjectid() {
        return fileobjectid;
    }

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
    public void setFileobjectid(Long fileobjectid) {
        this.fileobjectid = fileobjectid;
    }

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