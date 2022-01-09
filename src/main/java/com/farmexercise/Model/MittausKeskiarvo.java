package com.farmexercise.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MittausKeskiarvo {
    
    private String location;
    private String sensortype;
    private Double valuemin;
    private Double valuemax;
    private Double valueavg;

    public MittausKeskiarvo(String location, String sensortype, Double valuemin, Double valuemax, Double valueavg) {
        this.location = location;
        this.sensortype = sensortype;
        this.valuemin = valuemin;
        this.valuemax = valuemax;
        this.valueavg = valueavg;
    }

    // Konstruktori joka luo uudet MittausKeskiarvo-olion ResultSet-olion perusteella
    public MittausKeskiarvo(ResultSet rs) throws SQLException {
        this.location = rs.getString("location");
        this.sensortype = rs.getString("sensortype");
        this.valuemin = rs.getDouble("valuemin");
        this.valuemax = rs.getDouble("valuemax");
        this.valueavg = rs.getDouble("valueavg");
    }

    // Getterit
    public String getLocation() {
        return location;
    }

    public String getSensortype() {
        return sensortype;
    }

    public Double getValuemin() {
        return valuemin;
    }

    public Double getValuemax() {
        return valuemax;
    }

    public Double getValueavg() {
        return valueavg;
    }
    
    
    // Setterit
    public void setId(String location) {
        this.location = location;
    }
    
    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
    }

    public void setValuemin(Double valuemin) {
        this.valuemin = valuemin;
    }

    public void setValuemax(Double valuemax) {
        this.valuemax = valuemax;
    }

    public void setValueavg(Double valueavg) {
        this.valueavg = valueavg;
    }

}
