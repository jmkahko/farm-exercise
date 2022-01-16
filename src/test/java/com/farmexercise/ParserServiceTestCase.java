package com.farmexercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.farmexercise.Service.ParserService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParserServiceTestCase {
    
    @Test
    void sensortype() {
        assertTrue(ParserService.tarkistaSensortype("temperature"));
        assertTrue(ParserService.tarkistaSensortype("rainFall"));
        assertTrue(ParserService.tarkistaSensortype("pH"));
    }

    @Test
    void sensortypeVirheellinen() {
        assertFalse(ParserService.tarkistaSensortype("temperture"));
        assertFalse(ParserService.tarkistaSensortype("rainFdsall"));
        assertFalse(ParserService.tarkistaSensortype("pfsH"));
    }

    @Test
    void lampotila() {
        assertTrue(ParserService.tarkistaLampotila("temperature", 4.0));
        assertTrue(ParserService.tarkistaLampotila("temperature", -3.0));
        assertTrue(ParserService.tarkistaLampotila("temperature", 50.0));
    }

    @Test
    void lampotilaVirheellinen() {
        assertFalse(ParserService.tarkistaLampotila("temperature", 1000.0));
        assertFalse(ParserService.tarkistaLampotila("temperature", -100.0));
        assertFalse(ParserService.tarkistaLampotila("temperature", 445.0));
    }

    @Test
    void sademaara() {
        assertTrue(ParserService.tarkistaSademaara("rainFall", 0.0));
        assertTrue(ParserService.tarkistaSademaara("rainFall", 100.0));
        assertTrue(ParserService.tarkistaSademaara("rainFall", 500.0));
    }

    @Test
    void sademaaraVirheellinen() {
        assertFalse(ParserService.tarkistaSademaara("rainFall", -1.0));
        assertFalse(ParserService.tarkistaSademaara("rainFall", 1100.0));
        assertFalse(ParserService.tarkistaSademaara("rainFall", 501.0));
    }

    @Test
    void ph() {
        assertTrue(ParserService.tarkistaPh("pH", 0.0));
        assertTrue(ParserService.tarkistaPh("pH", 5.0));
        assertTrue(ParserService.tarkistaPh("pH", 14.0));
    }

    @Test
    void phVirheellinen() {
        assertFalse(ParserService.tarkistaPh("pH", -1.0));
        assertFalse(ParserService.tarkistaPh("pH", 14.1));
        assertFalse(ParserService.tarkistaPh("pH", 140.0));
    }
}