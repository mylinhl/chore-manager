package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage elements for chore page
 */
@Controller
public class ChoreController {

    private static final Logger log = LoggerFactory.getLogger(ChoreController.class);
    private final ChoreRepository choreRepository;

    private final List<Chore> chores = new ArrayList<>();

    public ChoreController(ChoreRepository choreRepository) {
        this.choreRepository = choreRepository;
    }

    @GetMapping("/chores")
    public String showChoreOverview(
            @RequestParam(required = false) String query,
            Model model) {

        List<Chore> chores;

        if (query != null && !query.isBlank()) {
            log.debug("Zoeken op query: {}", query);
            chores = choreRepository.findAll().stream()
                    .filter(chore -> chore.getChoreName()
                            .toLowerCase()
                            .contains(query.toLowerCase()))
                    .toList();
        } else {
            chores = choreRepository.findAll();
        }

        log.debug("Huishoud taken overzicht opgevraagd");
        model.addAttribute("paginaTitel", "Huishoudtaken Overzicht");
        model.addAttribute("chores", chores);
        model.addAttribute("query", query);
        return "chores";
    }

    @GetMapping("/chores/add")
    public String addChoreForm(Model model) {
        log.debug("Formulier voor nieuwe huishoud taak opgevraagd");
        model.addAttribute("paginaTitel", "Huishoud Taak Toevoegen");
        model.addAttribute("chore", new Chore());
        return "add-chore";
    }

    @PostMapping("/chores/add")
    public String processAddChore(@ModelAttribute Chore chore) {
        log.info("Nieuwe huishoud taak toegevoegd: {}", chore.getChoreName());
        chores.add(chore);
        return "redirect:/chores";
    }

    @GetMapping("/chores/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Chore> chore = choreRepository.findById(id);

        log.info("Bewerkformulier geopend voor: {}", id);

        if (chores.isEmpty()) {
            log.warn("Huishoud taak niet gevonden met id: {}", id);
            return "redirect:/chores";
        }

        model.addAttribute("chore", chore.get());
        model.addAttribute("allChores", choreRepository.findAll());
        return "add-chore";
    }

    @GetMapping("/chores/delete/{id}")
    public String deleteChore(@PathVariable Long id) {
        log.info("Verwijderverzoek voor taak: {}", id);
        choreRepository.deleteById(id);
        return "redirect:/chores";
    }

    @PostMapping("/chores/save")
    public String saveChore(
            @Valid @ModelAttribute Chore chore,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        log.info("Taak opslaan: {}", chore.getChoreName());

        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}", bindingResult.getErrorCount());
            return "add-chore";
        }

        choreRepository.save(chore);
        log.info("Huishoud taak opgeslagen: {}", chore.getChoreName());
        redirectAttributes.addFlashAttribute("successMessage", "Taak succesvol toegevoegd!");
        return "redirect:/chores";
    }
}
