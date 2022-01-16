package com.farmexercise.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;

import com.farmexercise.Model.Farm;
import com.farmexercise.Model.FileObject;
import com.farmexercise.Model.Measurement;
import com.farmexercise.Repository.FarmRepository;
import com.farmexercise.Repository.FileObjectRepository;
import com.farmexercise.Repository.MeasurementRepository;
import com.farmexercise.Service.ParserService;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileObjectController {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private FarmRepository farmRepository;

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

    // Tallennetaan tiedosto tietokantaan ja palvelimelle. Palvelimen tiedostosta
    // parseroidaan tietokantaan
    @PostMapping("/tallennaMittaus")
    public String tallenna(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {

        // Tarkistetaan onko liitetiedostoa
        if (file.isEmpty()) {
            System.out.println("Tiedostoa ei ole");
            attributes.addFlashAttribute("message", "Valitse ladattava tiedosto");
            return "redirect:/tallennaMittaus";
        }

        // Tuodaan FileObject modelli
        FileObject fo = new FileObject();

        // Tallennetaan tiedosto myös levylle ./LadatutTiedostot kansioon
        OutputStream out = null;

        String tiedostonNimi = LocalDateTime.now() + "-" + file.getOriginalFilename();

        // Tallennetaan FileObject modelliin tiedot
        fo.setContent(file.getBytes());
        fo.setTiedostonnimi(tiedostonNimi);
        fo.setMediatyyppi(file.getContentType());
        fo.setTiedostonkoko(file.getSize());
        fo.setTallennettu(LocalDateTime.now());

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
        String farmiName = "";

        // Luetaan tiedosto
        try (Scanner tiedostonLukija = new Scanner(new File("./LadatutTiedostot/" + tiedostonNimi))) {

            // Käydään tiedoston rivit yksikerrallaan läpi
            while (tiedostonLukija.hasNextLine()) {

                // Luetaan rivi muuttujaan tiedoston rivi
                String rivi = tiedostonLukija.nextLine();
                // Luodaan string muotoinen taulukko johon laitetaan csv-tiedostosta rivitiedot
                // taltteen erottimena käytetään pilkkua
                String[] palat = rivi.split(",");

                // Tarkistetaan onko kaikissa sarakkeissa tietoa. Data may be missing from
                // certain dates
                if (palat.length > 3) {

                    // Tarkistetaan onko value tai NULL sanaa
                    if (!palat[3].equals("value") & !palat[3].equals("NULL")) { 

                        // Tuodaan omiin muuttujiin arvot
                        String location = palat[0];
                        String datetime = palat[1];
                        String sensortype = palat[2];
                        Double value = Double.valueOf(palat[3]); // Muutetaan string double muotoon

                        // Tarkistetaan onhan sensorien tyypit oikein. Accept only temperature,rainfall
                        // and PH data. Other metrics should be discarded
                        if (ParserService.tarkistaSensortype(sensortype)) {

                            // Onko lisäys tietokantaan mahdollinen
                            Boolean lisataanko = false;

                            // Tarkistetaan onko lämpötila halutussa rajoissa
                            if (ParserService.tarkistaLampotila(sensortype, value)) {
                                lisattyTemperature++;
                                lisataanko = true;
                            }

                            // Tarkistaan onko sademäärä halutuissa rajoissa
                            if (ParserService.tarkistaSademaara(sensortype, value)) {
                                lisattyRainFall++;
                                lisataanko = true;
                            }

                            // Tarkistetaan onko pH arvo halutussa rajoissa
                            if (ParserService.tarkistaPh(sensortype, value)) {
                                lisattypH++;
                                lisataanko = true;
                            }

                            // Tuodaan Measurement model tallennusta varten ja alusteen jokaisella
                            // kierroksella
                            Measurement mittaus = new Measurement();

                            // Jos tarkistuksessa tuli TRUE niin tieto lisätään tietokantaan
                            if (lisataanko == true) {
                                // Haetaan Farm model
                                Farm farmi = new Farm();

                                // Tarkistetaan löytyykö farmi tallennettava farmi, jos ei löydy niin luodaan
                                // uusi farmi Farm-tietokantaan
                                if (farmRepository.findByFarmi(location) == null) {
                                    System.out.println("Luodaan uusi farmi: " + location);
                                    farmi.setFarmi(location);
                                    farmRepository.save(farmi);
                                }

                                // Tänne lisäys komento tietokantaan
                                mittaus.setFileobjectid(fo.getId()); // Haetaan FileObject tauluun tallennetun rivin ID
                                mittaus.setLocation(location);
                                mittaus.setDatetime(Instant.parse(datetime));
                                mittaus.setSensortype(sensortype);
                                mittaus.setValue(value);
                                measurementRepository.save(mittaus);
                                lisatty++;

                                // Listään farmiName muuttujaan farmin nimi
                                farmiName = location;
                            }
                        } else {
                            virhe++;
                            System.out.println("VIRHE!!!! Sensori tai arvo virheellinen " + location + ", Päiväys: "
                                    + datetime + ", Anturityyppi: " + sensortype + ", Arvo: " + value);
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
        System.out.println("Läpi käydyt rivit: " + (riveja - 1)); // Muuten kaikki paitsi ei lueta eka riviä kun otsikko
        System.out.println(
                "Lisätyt! Lämpötila: " + lisattyTemperature + ", pH: " + lisattypH + ", sademäärä: " + lisattyRainFall);

        // Näytetään käyttäjälle, kun tiedosto on ladattu
        attributes.addFlashAttribute("message", "Farmin: " + farmiName + " -tiedot ladattu");
        attributes.addFlashAttribute("messageLataus", "Lisätty: " + lisattyTemperature + " lämpötilaa, " + lisattypH
                + " pH-arvoa, " + lisattyRainFall + " sadearvoa");
        attributes.addFlashAttribute("messageHylatyt", "Yhteensä lisätty " + lisatty + " riviä ja hylätty " + virhe);
        return "redirect:/tallennaMittaus";
    }

    // Poistetaan haluttu tiedosto
    @Transactional // Ilman tätä tulee lob stream virhettä. Tietokanta ei pysty tekemään
                   // automaattista committia ja kyseessä isot tiedostot
    @PostMapping("/poistaMittaus/{id}")
    public String poista(@PathVariable Long id) {

        // Tuodaan FileObject modelli
        FileObject fo = fileObjectRepository.getById(id);

        // Poistetaan tiedosto palvelimen kiintolevyltä
        File tiedosto = new File("./LadatutTiedostot/" + fo.getTiedostonnimi());
        tiedosto.delete();

        // Poistetaan tiedot tietokannasta
        fileObjectRepository.deleteById(id);

        return "redirect:/tallennaMittaus";
    }
}
