package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author My Linh Lu
 * Manage elements for index page
 */
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String showIndex(Model model) {
        log.debug("Startpagina getoond om {}", LocalTime.now());
        model.addAttribute("paginaTitel", "Huishoud Manager");
        model.addAttribute("naam", "My Linh Lu");
        model.addAttribute("datum", LocalDate.now().toString());
        return "index";
    }
}
