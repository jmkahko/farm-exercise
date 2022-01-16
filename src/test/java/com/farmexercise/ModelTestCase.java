package com.farmexercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import com.farmexercise.Model.Farm;
import com.farmexercise.Model.FileObject;
import com.farmexercise.Model.Measurement;
import com.farmexercise.Model.MittauksiaYhteensa;
import com.farmexercise.Model.MittausKeskiarvo;
import com.farmexercise.Model.MittausKuukausi;
import com.farmexercise.Model.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelTestCase {

    @Test
    void farm() {
        // Luodaan uusi farmi ja tarkistetaan, että tiedot löytyvät
        Farm farm = new Farm(1L, "Testifarmi");

        assertEquals("Testifarmi", farm.getFarmi());
        assertEquals(1L, farm.getId());
    }

    @Test
    void measurement() {
        // Luodaan uusi mittaus ja tarkistetaan, että tiedot löytyvät
        Measurement measurement = new Measurement(1L, 1L, "Testifarmi", Instant.parse("2018-12-31T22:00:00.000Z"), "pH", -1.0);

        assertEquals(1L, measurement.getId());
        assertEquals(1L, measurement.getFileobjectid());
        assertEquals("Testifarmi", measurement.getLocation());
        assertEquals(Instant.parse("2018-12-31T22:00:00.000Z"), measurement.getDatetime());
        assertEquals("pH", measurement.getSensortype());
        assertEquals(-1.0, measurement.getValue());
    }

    @Test
    void fileObject() {
        // Luodaan uusi fileobject ja testataan osa tiedoista
        FileObject fo = new FileObject(1L, null, "Testi tiedosto", "csv", 123456L, null);
        
        assertEquals(1L, fo.getId());
        assertEquals("csv", fo.getMediatyyppi());
        assertEquals(123456L, fo.getTiedostonkoko());
        assertEquals("Testi tiedosto", fo.getTiedostonnimi());
    }

    @Test
    void mittauksiaYhteensa() {
        // Luodaan uusi mittausYhteensa ja testaan osa, että annetut tiedot löytyvät
        MittauksiaYhteensa my = new MittauksiaYhteensa("Testi farmi", "pH", 5, null, null);

        assertEquals("Testi farmi", my.getLocation());
        assertEquals("pH", my.getSensortype());
        assertEquals(5, my.getMittauksia());
    }

    @Test
    void mittausKeskiarvo() {
        // Luodaan uusi mittausKeskiarvo ja testaan, että annetut tiedot löytyvät
        MittausKeskiarvo mk = new MittausKeskiarvo("Testi farmi", "pH", -1.0, 5.0, 0.0);

        assertEquals("Testi farmi", mk.getLocation());
        assertEquals("pH", mk.getSensortype());
        assertEquals(-1.0, mk.getValuemin());
        assertEquals(0.0, mk.getValueavg());
        assertEquals(5.0, mk.getValuemax());
    }

    @Test
    void mittausKuukausi() {
        // Luodaan uusi mittausKuukausi ja testaan, että annetut tiedot löytyvät
        MittausKuukausi mk = new MittausKuukausi("2022-01", -1.0, 5.0, 2.0);

        assertEquals("2022-01", mk.getKuukausi());
        assertEquals(-1.0, mk.getValuemin());
        assertEquals(2.0, mk.getValueavg());
        assertEquals(5.0, mk.getValuemax());
    }

    @Test
    void user() throws NoSuchAlgorithmException {
        // Luodaan uusi käyttäjä
        User user = new User(1L, "Janne", "jannenSalasana", null);

        assertEquals(1L, user.getId());
        assertEquals("Janne", user.getUsername());
        assertEquals("jannenSalasana", user.getPassword());
     }   
}
