package com.farmexercise.Service;

public interface ParserService {

    // Tarkistetaan onko sensorityyppi temperature, rainFall tai pH
    public static Boolean tarkistaSensortype(String sensortype) {
        
        if (sensortype.equals("temperature") || sensortype.equals("rainFall") || sensortype.equals("pH")) {
            return true;
        } else {
            return false;
        }
    }
    
    // Tarkistetaan onko saatu arvo rainFall ja sademäärä on positiivinen luku välillä 0 ja 500
    public static Boolean tarkistaSademaara(String sensortype, Double maara) {

        if (sensortype.equals("rainFall")) {
            if (maara >= 0 && maara <= 500) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Tarkistetaan onko arvo temperature ja lämpötila -50 ja 100 arvon väliltä
    public static Boolean tarkistaLampotila(String sensortype, Double maara) {

        if (sensortype.equals("temperature")) { 
            if (maara >= -50 && maara <= 100) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Tarkistetaan onko saatu arvo pH ja desimaaliarvo välillä 0-14
    public static Boolean tarkistaPh(String sensortype, Double maara) {
        if (sensortype.equals("pH")) { 
            if (maara >= 0.0 && maara <= 14.0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
