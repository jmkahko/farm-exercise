package com.farmexercise.Controller;

import java.io.File;
import java.util.Scanner;
import com.farmexercise.Model.Measurement;
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

    @GetMapping("/tallennaMittaus")
    public String tiedostonLataus() {
        System.out.println("Tiedoston lataus alkaa");
        int virhe = 0;
        int lisatty = 0;
        int riveja = 0;
        int lisattypH = 0;
        int lisattyTemperature = 0;
        int lisattyRainFall = 0;

        // Luetaan tiedosto
        try (Scanner tiedostonLukija = new Scanner(new File("./pohjadata/PartialTech.csv"))) {

            // Käydään tiedoston rivit yksikerrallaan läpi
            while (tiedostonLukija.hasNextLine()) {

                // Luetaan rivi muuttujaan tiedoston rivi
                String rivi = tiedostonLukija.nextLine();
                // Luodaan string muotoinen taulukko johon laitetaan csv-tiedostosta rivitiedot taltteen erottimena käytetään pilkkua
                String[] palat = rivi.split(",");

                // Tarkistetaan onko kaikissa sarakkeissa tietoa. Data may be missing from certain dates
                if (palat.length > 3) {

                    // Tarkistetaan onko value tai NULL sanaa
                    if (!palat[3].equals("value") & !palat[3].equals("NULL")) {

                        // Tuodaan omiin muuttujiin arvot
                        String location = palat[0];
                        String datetime = palat[1];
                        String sensortype = palat[2];
                        Double value = Double.valueOf(palat[3]); // Muutetaan string double muotoon
    
                        // Tarkistetaan onhan sensorien tyypit oikein. Accept only temperature,rainfall and PH data. Other metrics should be discarded
                        if (sensortype.equals("temperature") || sensortype.equals("rainFall") || sensortype.equals("pH")) {
                            
                            // Onko lisäys tietokantaan mahdollinen
                            Boolean lisataanko = false;

                            // Tarkistetaan onko lämpötila halutussa rajoissa
                            if (sensortype.equals("temperature")) {
                                // Lämpötila on celsiusarvo -50 ja 100 välillä
                                if (value >= -50 && value <= 100) {
                                    lisattyTemperature++;
                                    lisataanko = true;
                                } else {
                                    System.out.println("VIRHE!!!! Lämpötila ei ole asteikolla: " + location + ", Päiväys: " + datetime + ", Anturityyppi: " + sensortype + ", Arvo: " + value);
                                    virhe++;
                                }
                            }
                            
                            // Tarkistaan onko sademäärä halutuissa rajoissa
                            if (sensortype.equals("rainFall")) {
                                // Sademäärä on positiivinen luku välillä 0 ja 500
                                if (value >= 0.0 && value <= 500) {
                                    lisattyRainFall++;
                                    lisataanko = true;
                                } else {
                                    System.out.println("VIRHE!!!! Sademäärä ei ole asteikolla: " + location + ", Päiväys: " + datetime + ", Anturityyppi: " + sensortype + ", Arvo: " + value);
                                    virhe++;
                                }
                            
                            } 
                        
                            // Tarkistetaan onko pH arvo halutussa rajoissa
                            if (sensortype.equals("pH")) {
                                // pH on desimaaliarvo välillä 0-14
                                if (value >= 0.0 && value <= 14.0) {
                                    lisattypH++;
                                    lisataanko = true;
                                } else {
                                    System.out.println("VIRHE!!!! pH arvo ei ole asteikolla: " + location + ", Päiväys: " + datetime + ", Anturityyppi: " + sensortype + ", Arvo: " + value);
                                    virhe++;
                                }
                            }
                            
                            // Jos tarkistuksessa tuli TRUE niin tieto lisätään tietokantaan
                            if (lisataanko == true) {
                                // Tänne lisäys komento tietokantaan
                                measurementRepository.save(new Measurement());
                                lisatty++;
                             }
                        } else {
                            virhe++;
                            System.out.println("VIRHE!!!! Sensori tyyppi virheellinen " + location + ", Päiväys: " + datetime + ", Anturityyppi: " + sensortype + ", Arvo: " + value);
                        }
                    }
                    
                } else {
                    System.out.println("VIRHE!!!! Liian vähän tietoa: " + palat[0]);
                }
                // Lisätään luetun rivin määrä
                riveja++;
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }

        // Tulostetaan lopputulos konsoliin
        System.out.println("Virheellisiä: " + virhe);
        System.out.println("Lisätyt rivit: " + lisatty);
        System.out.println("Läpi käydyt rivit: " + (riveja-1)); // Muuten kaikki paitsi ei lueta eka riviä kun otsikko
        System.out.println("Lisätyt! Lämpötila: " + lisattyTemperature + ", pH: " + lisattypH + ", sademäärä: " + lisattyRainFall);
        
        return "mittaus";
    }

}