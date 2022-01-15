package com.farmexercise.Controller;

import com.farmexercise.Model.User;
import com.farmexercise.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/lisaaKayttaja")
    public String lisaaKayttaja() {

        User kayttaja = new User();

        kayttaja.setUsername("Käyttäjä");
        kayttaja.setPassword("Salasana");

        userRepository.save(kayttaja);

        
        
        return "mittaus";
    }


    

}
