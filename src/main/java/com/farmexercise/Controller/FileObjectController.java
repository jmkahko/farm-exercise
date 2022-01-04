package com.farmexercise.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Scanner;

import com.farmexercise.Model.FileObject;
import com.farmexercise.Model.Measurement;
import com.farmexercise.Repository.FileObjectRepository;
import com.farmexercise.Repository.MeasurementRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileObjectController {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    public Boolean liite;
    
    @GetMapping("/tallennaMittaus")
    public String haeKaikki(Model model) {
        model.addAttribute("tiedosto", fileObjectRepository.findAll());
        return "tallennaMittaus";
    }

    @Transactional // Ilman tätä tulee lob stream virhettä
    @GetMapping("/tallennaMittaus/{id}")
    public ResponseEntity<byte[]> haeTiedosto(@PathVariable Long id) {
        // Haetaan halutun rivin id tieto
        FileObject fo = fileObjectRepository.getById(id);

        final HttpHeaders headers = new HttpHeaders(); 
        
        headers.setContentType(MediaType.parseMediaType(fo.getMediatyyppi()));
        headers.setContentLength(fo.getTiedostonkoko());
        headers.add("Content-Disposition", "attachment; filename=" + fo.getTiedostonnimi());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED); 
    }

    // Tallennetaan tiedosto tietokantaan ja palvelimelle. Palvelimen tiedostosta parseroidaan tietokantaan
    @PostMapping("/tallennaMittaus")
    public String tallenna(@RequestParam("file") MultipartFile file) throws IOException {

        // Tarkistetaan onko liitetiedostoa
        if (file.isEmpty()) {
            System.out.println("Tiedostoa ei ole");
            liite = false;
        } else {
            System.out.println("Tiedosto löytyy");
            liite = true;

            // Tuodaan FileObject modelli
            FileObject fo = new FileObject();

            // Tallennetaan tiedosto myös levylle ./LadatutTiedostot kansioon
            OutputStream out = null;

            String tiedostonNimi = Instant.now().toString() + "-" + file.getOriginalFilename();

            // Tallennetaan FileObject modelliin tiedot
            fo.setContent(file.getBytes());
            fo.setTiedostonnimi(tiedostonNimi);
            fo.setMediatyyppi(file.getContentType());
            fo.setTiedostonkoko(file.getSize());
            fo.setTallennettu(Instant.now().toString());
            
            // Tallennetaan tietokantaan tallennettava tiedosto
            fileObjectRepository.save(fo);

            // Luodaan halutun päätteinen tiedosto
            out = new FileOutputStream(new File("./LadatutTiedostot/" + tiedostonNimi));

            out.write(file.getBytes()); // Kirjoitetaan out muuttujaan ladatun tiedoston tiedot
            out.flush(); // Tallennetaan data tiedostoon
            out.close(); // Suljetaan tiedoston tallennus

            System.out.println("Tiedosto ladattu");

            int virhe = 0;
            int lisatty = 0;
            int riveja = 0;
            int lisattypH = 0;
            int lisattyTemperature = 0;
            int lisattyRainFall = 0;

            // Luetaan tiedosto
            try (Scanner tiedostonLukija = new Scanner(new File("./LadatutTiedostot/" + tiedostonNimi))) {

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
                                
                                // Tuodaan Measurement model tallennusta varten ja alusteen jokaisella kierroksella
                                Measurement mittaus = new Measurement();

                                // Jos tarkistuksessa tuli TRUE niin tieto lisätään tietokantaan
                                if (lisataanko == true) {
                                    // Tänne lisäys komento tietokantaan
                                    mittaus.setLocation(location);
                                    mittaus.setDatetime(datetime);
                                    mittaus.setSensortype(sensortype);
                                    mittaus.setValue(value);
                                    measurementRepository.save(mittaus);
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
        }    
        

        return "redirect:/tallennaMittaus"; 
    }

    // Poistetaan haluttu tiedosto tietokannasta
    @Transactional // Ilman tätä tulee lob stream virhettä. Tietokanta ei pysty tekemään automaattista committia ja kyseessä isot tiedostot
    @PostMapping("/poistaMittaus/{id}")
    public String poista(@PathVariable Long id) {
        
        // Tuodaan FileObject modelli
        FileObject fo = fileObjectRepository.getById(id);

        // Poistetaan tiedosto palvelimen kiintolevyltä
        File tiedosto = new File("./LadatutTiedostot/" + fo.getTiedostonnimi());
        tiedosto.delete();

        // Poistetaan haluttu rivi tietokannasta
        fileObjectRepository.deleteById(id);

        return "redirect:/tallennaMittaus"; 
    }
}
