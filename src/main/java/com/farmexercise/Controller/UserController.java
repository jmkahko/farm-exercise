package com.farmexercise.Controller;

import com.farmexercise.Repository.FarmRepository;
import com.farmexercise.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FarmRepository farmRepository;

    @GetMapping("/lisaaKayttaja")
    public String lisaaKayttaja() {
        

        return null;
    }
}
