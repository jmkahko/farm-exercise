package com.farmexercise.Controller;

import java.time.Instant;
import java.util.List;

import com.farmexercise.Model.MittauksiaYhteensa;
import com.farmexercise.Model.MittausKeskiarvo;
import com.farmexercise.Model.MittausKuukausi;
import com.farmexercise.Repository.FarmRepository;
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
    private FarmRepository farmRepository;

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

    // Tietyn farmin ja anturin mittaukset
    @GetMapping("/haeMaatilanJaAnturinMittaukset/{maatila}/{sensortype}")
    public String haeMaatilanKaikkiMittaukset(Model model, @PathVariable String maatila,
            @PathVariable String sensortype) {
        model.addAttribute("mittaus", measurementRepository.findByLocationAndSensortype(maatila, sensortype));
        return "mittaus";
    }

    // Farmin tietyn sensorin kuukausittaiset keskiarvot ja min/max
    @GetMapping("/kuukausittaisetKeskiarvot/{maatila}/{sensori}")
    public String kuukausittaisetKeskiarvot(Model model, @PathVariable String maatila, @PathVariable String sensori) {

        // Haetaan data MittausKuukausi -luokkaan
        List<MittausKuukausi> mittausKuukausi = jdbcTemplate.query(
                "SELECT DATE_TRUNC('month', datetime) as kuukausi, MIN(value) as valueMin, MAX(value) as valueMax, AVG(value)::numeric(10,2) as valueavg FROM measurement " +
                "WHERE location = ? AND sensortype = ? GROUP BY DATE_TRUNC('month', datetime) ORDER BY kuukausi DESC",
                (rs, rowNum) -> new MittausKuukausi(rs),
                maatila, sensori);

        model.addAttribute("mittausKuukausi", mittausKuukausi);

        return "anturiKeskiarvot";
    }

    // Farmin sensorien keskiarvot ja min/max ja mittausten määrä yhteensä
    @GetMapping("/haeFarminArvot/{maatila}")
    public String kuukausittaisetKeskiarvot(Model model, @PathVariable String maatila) {

        // Haetaan data MittausKeskiarvo -luokkaan
        List<MittausKeskiarvo> sensorit = jdbcTemplate.query(
                "SELECT location, sensortype, MIN(value) as valueMin, MAX(value) as valueMax, AVG(value)::numeric(10,2) as valueavg FROM measurement "
                        +
                        "WHERE location = ? " +
                        "GROUP BY location, sensortype;",
                (rs, rowNum) -> new MittausKeskiarvo(rs),
                maatila);

        // Haetaan miten paljon mittauksia yhteensa
        List<MittauksiaYhteensa> mittauksiaYhteensa = jdbcTemplate.query(
                "SELECT location, sensortype, min(datetime) as alkaa, max(datetime) as loppuu, count(sensortype) as mittauksia FROM measurement " +
                        "WHERE location = ? " +
                        "GROUP BY location, sensortype;",
                (rs, rowNum) -> new MittauksiaYhteensa(rs),
                maatila);

        // Viedään mittaukset HTML-sivulle
        model.addAttribute("farminimi", farmRepository.findByFarmi(maatila));
        model.addAttribute("tulos", sensorit);
        model.addAttribute("yhteensaTuloksia", mittauksiaYhteensa);

        return "farmi";
    }

    // Tietyn farmin ja anturin mittaukset tietyltä aikaväliltä
    @GetMapping("/haeMaatilanTietynAikavalinMittaukset/{maatila}/{sensortype}/{alkuAika}/{loppuAika}")
    public String haeMaatilanTietynAikavalinMittaukset(Model model, @PathVariable String maatila, @PathVariable String sensortype, @PathVariable Instant alkuAika,  @PathVariable Instant loppuAika)  {
        model.addAttribute("mittaus", measurementRepository.findByDatetimeBetweenAndLocationAndSensortype(alkuAika, loppuAika, maatila, sensortype));
        return "mittaus";
    }

}