package com.farmexercise.Controller;

import java.util.List;
import java.util.Map;

import com.farmexercise.Repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MeasurementController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Haetaan kaikki mittaustulokset tietokannasta
    @GetMapping("/mittaus")
    public String haeMittaukset(Model model) {

        // Tarkistetaan onko mittaustuloksia, jos ei ole niin ohjataan tallennus sivulle
        if (measurementRepository.count() == 0) {
            return "tallennaMittaus";
        } else {
            model.addAttribute("mittaus", measurementRepository.findAll());
            return "mittaus";
        }
        
    }

    // Tietyn farmin kaikki mittaukset
    // SELECT * FROM measurement WHERE location = 'FARMI'
    @GetMapping("/haeMaatilanKaikkiMittaukset/{maatila}")
    public String haeMaatilanKaikkiMittaukset(Model model, @PathVariable String maatila){
        model.addAttribute("mittaus", measurementRepository.findByLocation(maatila));
        return "mittaus";
    }

    // Farmin sensorien halutun aikavälin min, max, keskiarvo
    //SELECT sensortype, MIN(value) as valueMin, MAX(value) as valueMax, AVG(value)::numeric(10,2) as value FROM measurement WHERE datetime <= 'ALKUAIKA' AND datetime >= 'LOPPUAIKA' AND location = 'FARMI' GROUP BY sensortype
    @GetMapping("/haluttuAikavaliMin/Max/Avg/{alkuPaiva}/{loppuPaiva}/{maatila}")
    public String haluttuAikavaliMinMaxAvg(Model model, @PathVariable String alkuPaiva, @PathVariable String loppuPaiva, @PathVariable String maatila) {
        // Saadaan haettua tietyltä aikaväliltä tietyn farmin tapahtumat
        //model.addAttribute("mittaus", measurementRepository.findByDatetimeBetweenAndLocation(alkuPaiva, loppuPaiva, maatila));
  
        //List<Measurement> tulos = measurementRepository.findByDatetimeBetweenAndLocation(alkuPaiva, loppuPaiva, maatila);


        
        List<Map<String, Object>> tulos = jdbcTemplate.queryForList(
            "SELECT sensortype, MIN(value) as valueMin, MAX(value) as valueMax, AVG(value)::numeric(10,2) as value FROM measurement" +
            "WHERE datetime =< ? AND datetime => ? AND location = ? GROUP BY sensortype;",
            alkuPaiva, loppuPaiva, maatila);
        
            System.out.println("Tulooos: " + tulos);
            model.addAttribute("mittaus", tulos);

            System.out.println("Tulos: " + tulos);


        return "mittaus";
    }

    // Farmin sensorien kuukausittaiset keskiarvot, min/max
    @GetMapping("/kuukausittaisetKeskiarvot/{maatila}/{sensori}")
    public String kuukausittaisetKeskiarvot(Model model, @PathVariable String maatila, @PathVariable String sensori) {
       

        return null;
    }

    // Farmin halutun aikavälin ja tietyn sensorin kaikkiarvot
    // SELECT * FROM measurement WHERE datetime <= 'ALKUAIKA AND datetime >= 'LOPPUAIKA' AND location = 'FARMI' AND sensortype ='SENSORI'
    @GetMapping("/haluttuAikavali/{alkuPaiva}/{loppuPaiva}/{maatila}/{sensori}")
    public String haluttuAikavali(Model model, @PathVariable String alkuPaiva, @PathVariable String loppuPaiva, @PathVariable String maatila, @PathVariable String sensori) {
        model.addAttribute("mittaus", measurementRepository.findByDatetimeBetweenAndLocationAndSensortype(alkuPaiva, loppuPaiva, maatila, sensori));
        return "mittaus";
    }

}