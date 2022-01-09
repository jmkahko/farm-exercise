package com.farmexercise.Controller;

import com.farmexercise.Repository.FarmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FarmController {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Haetaan kaikki farmit
    @GetMapping("/farmit")
    public String haeFarmit(Model model) {

        model.addAttribute("farmit", farmRepository.findAll());

        return "farmit";
    }
}
