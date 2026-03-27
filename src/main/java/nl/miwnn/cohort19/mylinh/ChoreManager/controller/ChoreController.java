package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final FamilyMemberRepository familyMemberRepository;

    public ChoreController(ChoreRepository choreRepository,
                           FamilyMemberRepository familyMemberRepository) {
        this.choreRepository = choreRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    @GetMapping("/chores")
    public String showChoreOverview(
            @RequestParam(required = false) String query,
            Model model) {

        List<Chore> chores = choreRepository.findAll();
        log.debug("Huishoud taken overzicht opgevraagd, {} taken aanwezig.", chores.size());

        List<Chore> displayChores;
        if (query != null && !query.isBlank()) {
            log.debug("Zoeken op query: {}", query);
            displayChores = choreRepository.findChoresByTitleContainingIgnoreCase(query);
        } else {
            displayChores = chores;
        }

        log.debug("Huishoud taken overzicht opgevraagd");
        model.addAttribute("paginaTitel", "Huishoudtaken Overzicht");
        model.addAttribute("chores", displayChores);
        return "chores";
    }

    @GetMapping("/chores/add")
    public String addChoreForm(Model model) {
        log.debug("Formulier voor nieuwe huishoud taak opgevraagd");
        model.addAttribute("paginaTitel", "Huishoud Taak Toevoegen");
        model.addAttribute("chore", new Chore());
        model.addAttribute("allFamilyMembers", familyMemberRepository.findAll());
        return "add-chore";
    }

    @PostMapping("/chores/add")
    public String processAddChore(@ModelAttribute Chore chore) {
        log.info("Nieuwe huishoud taak toegevoegd: {}", chore.getChoreName());
        choreRepository.save(chore);
        return "redirect:/chores";
    }

    @GetMapping("/chores/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Chore> chore = choreRepository.findById(id);

        log.info("Bewerkformulier geopend voor: {}", id);

        if (chore.isEmpty()) {
            log.warn("Huishoud taak niet gevonden met id: {}", id);
            return "redirect:/chores";
        }

        model.addAttribute("chore", chore.get());
        model.addAttribute("allFamilyMembers", familyMemberRepository.findAll());
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

    @GetMapping({"/{id}", "/detail/{id}"})
    public String showChoreDetail(
            @PathVariable Long id, Model model) {
        Optional<Chore> chore = choreRepository.findById(id);

        if (chore.isEmpty()) {
            return "redirect:/chores";
        }

        model.addAttribute("chore", chore.get());
        return "chore-detail";
    }
}
