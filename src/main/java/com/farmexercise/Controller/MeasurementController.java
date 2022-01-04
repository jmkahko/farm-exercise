package com.farmexercise.Controller;

import com.farmexercise.Repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeasurementController {

    @Autowired
    private MeasurementRepository measurementRepository;

    // Haetaan kaikki mittaustulokset tietokannasta
    @GetMapping("/mittaus")
    public String getMittaukset(Model model) {

        // Tarkistetaan onko mittaustuloksia, jos ei ole niin ohjataan tallennus sivulle
        if (measurementRepository.count() == 0) {
            return "tallennaMittaus";
        } else {
            model.addAttribute("mittaus", measurementRepository.findAll());
            return "mittaus";
        }
        
    }

    

}