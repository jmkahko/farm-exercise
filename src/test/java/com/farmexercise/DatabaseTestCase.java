package com.farmexercise;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseTestCase {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    // Tarkistetaan onko tietokanta luotu
    @Test
    public void onkoTietokantaLuotu() {
        // Haetaan rivien määrä taulukkoon. Ilman taulukkoa tulee injectio virhe
        List<Object> fo = jdbcTemplate.query(
            "SELECT COUNT(*) AS maara FROM information_schema.tables;",
            (rs, rowNum) -> rs.getString("maara"));

        // Otetaan maara string muuttujaan taulukon ensimmäinen arvo
        String maara = (String) fo.get(0);

        // Muutetaan maara arvo numero muotoon ja tarkistetaan, että onko arvo suurempi tai yhtä suuri kuin 0
        assertTrue(0 <= Integer.parseInt(maara));
    }

    // Fileobject -taulu
    @Test
    public void onkoTietokantatauluFileobjectLuotu() {
        // Haetaan rivien määrä taulukkoon. Ilman taulukkoa tulee injectio virhe
        List<Object> fo = jdbcTemplate.query(
            "SELECT COUNT(*) AS maara FROM fileobject;",
            (rs, rowNum) -> rs.getString("maara"));

        // Otetaan maara string muuttujaan taulukon ensimmäinen arvo
        String maara = (String) fo.get(0);

        // Muutetaan maara arvo numero muotoon ja tarkistetaan, että onko arvo suurempi tai yhtä suuri kuin 0
        assertTrue(0 <= Integer.parseInt(maara));
    }

    // Measurement -taulu
    @Test
    public void onkoTietokantatauluMeasurementLuotu() {
        // Haetaan rivien määrä taulukkoon. Ilman taulukkoa tulee injectio virhe
        List<Object> fo = jdbcTemplate.query(
            "SELECT COUNT(*) AS maara FROM measurement;",
            (rs, rowNum) -> rs.getString("maara"));

        // Otetaan maara string muuttujaan taulukon ensimmäinen arvo
        String maara = (String) fo.get(0);

        // Muutetaan maara arvo numero muotoon ja tarkistetaan, että onko arvo suurempi tai yhtä suuri kuin 0
        assertTrue(0 <= Integer.parseInt(maara));
    }

    // Farm -taulu
    @Test
    public void onkoTietokantatauluFarmLuotu() {
        // Haetaan rivien määrä taulukkoon. Ilman taulukkoa tulee injectio virhe
        List<Object> fo = jdbcTemplate.query(
            "SELECT COUNT(*) AS maara FROM farm;",
            (rs, rowNum) -> rs.getString("maara"));

        // Otetaan maara string muuttujaan taulukon ensimmäinen arvo
        String maara = (String) fo.get(0);

        // Muutetaan maara arvo numero muotoon ja tarkistetaan, että onko arvo suurempi tai yhtä suuri kuin 0
        assertTrue(0 <= Integer.parseInt(maara));
    }
    
}
