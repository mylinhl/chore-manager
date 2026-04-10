package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author My Linh Lu
 * Manage elements for about us page
 */
@Controller
@RequestMapping("/about-us")
public class AboutUsController {
    private static final Logger log = LoggerFactory.getLogger(ChoreController.class);

    @GetMapping("")
    public String showAboutUs(Model model) {

        log.debug("Over ons pagina opgevraagd");
        model.addAttribute("paginaTitel", "Over Ons");
        model.addAttribute("activePage", "about-us");
        return "about-us";
    }
}
