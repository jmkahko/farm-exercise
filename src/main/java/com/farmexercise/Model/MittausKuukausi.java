package com.farmexercise.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MittausKuukausi {
    private String kuukausi;
    private Double valuemin;
    private Double valuemax;
    private Double valueavg;

    public MittausKuukausi(String kuukausi, Double valuemin, Double valuemax, Double valueavg) {
        this.kuukausi = kuukausi;
        this.valuemin = valuemin;
        this.valuemax = valuemax;
        this.valueavg = valueavg;
    }

    // Konstruktori joka luo uudet MittausKeskiarvo-olion ResultSet-olion perusteella
    public MittausKuukausi(ResultSet rs) throws SQLException {
        this.kuukausi = rs.getString("kuukausi");
        this.valuemin = rs.getDouble("valuemin");
        this.valuemax = rs.getDouble("valuemax");
        this.valueavg = rs.getDouble("valueavg");
    }

    // Getterit
    public String getKuukausi() {
        return kuukausi;
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
    public void setId(String kuukausi) {
        this.kuukausi = kuukausi;
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
