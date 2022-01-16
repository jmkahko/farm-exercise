package com.farmexercise;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseTestCase {

    @Mock
    JdbcTemplate jdbcTemplate;
    
    // Onko tietokantaa luotu
    @Test
    public void onkoTietokantaLuotu() {
       
        
    }

    // Onko tietokantataulut luotu
    // Fileobject -taulu
    @Test
    public void onkoTietokantatauluFileobjectLuotu() {

    }

    // Measurement -taulu
    @Test
    public void onkoTietokantatauluMeasurementLuotu() {
        
    }

    // Farm -taulu
    @Test
    public void onkoTietokantatauluFarmLuotu() {

    }
    
}
