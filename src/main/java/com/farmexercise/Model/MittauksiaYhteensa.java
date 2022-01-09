package com.farmexercise.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MittauksiaYhteensa {
    private String location;
    private String sensortype;
    private Integer mittauksia;
    private Timestamp alkaa;
    private Timestamp loppuu;

    public MittauksiaYhteensa(String location, String sensortype, Integer mittauksia, Timestamp alkaa, Timestamp loppuu) {
        this.location = location;
        this.sensortype = sensortype;
        this.mittauksia = mittauksia;
        this.alkaa = alkaa;
        this.loppuu = loppuu;
    }

    // Konstruktori joka luo uudet MittauksiaYhteensa-olion ResultSet-olion perusteella
    public MittauksiaYhteensa(ResultSet rs) throws SQLException {
        this.location = rs.getString("location");
        this.sensortype = rs.getString("sensortype");
        this.mittauksia = rs.getInt("mittauksia");
        this.alkaa = rs.getTimestamp("alkaa");
        this.loppuu = rs.getTimestamp("loppuu");
    }

    // Getterit
    public String getLocation() {
        return location;
    }

    public String getSensortype() {
        return sensortype;
    }

    public Integer getMittauksia() {
        return mittauksia;
    }

    public Timestamp getAlkaa() {
        return alkaa;
    }

    public Timestamp getLoppuu() {
        return loppuu;
    }
    
    // Setterit
    public void setId(String location) {
        this.location = location;
    }
    
    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
    }

    public void setMittauksia(Integer mittauksia) {
        this.mittauksia = mittauksia;
    }

    public void setAlkaa(Timestamp alkaa) {
        this.alkaa = alkaa;
    }

    public void setLoppuu(Timestamp loppuu) {
        this.loppuu = loppuu;
    }
}
