package com.farmexercise;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.farmexercise.Model.Farm;
import com.farmexercise.Repository.FarmRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoutesTestCase {

    @Autowired 
    TestRestTemplate testRestTemplate;

    @Autowired
    private FarmRepository farmRepository;

    @Test
    void index() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
    }

    @Test
    void indexLength() {
        String body = testRestTemplate.getForObject("/", String.class);
        assertEquals(5926, body.length()); // Sanoman pituus on sama
    }

    // Testaan reitti virheellisillä arvoilla, mutta oikealla rakenteella
    @Test
    void anturiKeskiarvot() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/kuukausittaisetKeskiarvot/ei ole/sensori", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
    }

    // Testaan reitti oikealla löytyvällä farmilla ja virheellisellä farmilla, mutta oikealla rakenteella
    @Test
    void farmi() {

        // Haetaan kaikki farmit
        List<Farm> farmi = farmRepository.findAll();

        // Testaan reitti farmilla joka löytyy tietokannasta
        if (farmRepository.count() > 0) {
            // Haetaan ensimmäisen farmin nimi
            String farmName = farmi.get(0).getFarmi();

            ResponseEntity<String> response = testRestTemplate.getForEntity("/haeFarminArvot/" + farmName, String.class);
            assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
        } else {
            ResponseEntity<String> response = testRestTemplate.getForEntity("/haeFarminArvot/ei ole", String.class);
            assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
        }
    }

    @Test
    void farmit() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/farmit", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
    }

    @Test
    void mittaus() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/mittaus", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
    }

    @Test
    void tallennaMittaus() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/tallennaMittaus", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK); // Saadaan status 200 vastaus
    }

    @Test
    void virheellinenReitti() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/virheellinenReitti", String.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND); // Saadaan status 404 not found
    }
}