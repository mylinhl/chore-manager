package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Image;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ImageRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.ChoreService;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.FamilyMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage elements for chore page
 */
@Controller
@RequestMapping("/chores")
public class ChoreController {

    private static final Logger log = LoggerFactory.getLogger(ChoreController.class);
    private final ChoreService choreService;
    private final FamilyMemberService familyMemberService;
    private final ImageRepository imageRepository;

    public ChoreController(ChoreService choreService,
                           FamilyMemberService familyMemberService,
                           ImageRepository imageRepository) {
        this.choreService = choreService;
        this.familyMemberService = familyMemberService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("")
    public String showChoreOverview(
            @RequestParam(required = false) Long familymember,
            Model model) {

        List<Chore> chores;

        if (familymember != null) {
            chores = choreService.getChoresByFamilyMemberId(familymember);
        } else {
            chores = choreService.getAllChores(null);
        }

        log.debug("Gezocht op familielid: {}", familymember);

        log.debug("Huishoud taken overzicht opgevraagd, {} taken aanwezig.", chores.size());
        model.addAttribute("paginaTitel", "Huishoudtaken Overzicht");
        model.addAttribute("chores", chores);
        model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
        model.addAttribute("activePage", "chores");
        return "chores";
    }

    @GetMapping("/add")
    public String addChoreForm(Model model) {
        log.debug("Formulier voor nieuwe huishoud taak opgevraagd.");
        model.addAttribute("paginaTitel", "Huishoud Taak Toevoegen");
        model.addAttribute("chore", new Chore());
        model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
        return "add-chore";
    }

    @PostMapping("/add")
    public String processAddChore(@ModelAttribute Chore chore) {
        choreService.saveChore(chore);
        return "redirect:/chores";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model) {

        Optional<Chore> chore = choreService.getChoreById(id);

        log.info("Bewerkformulier geopend voor: {}", id);

        if (chore.isEmpty()) {
            log.warn("Huishoud taak niet gevonden met id: {}", id);
            return "redirect:/chores";
        }

        model.addAttribute("paginaTitel", "Huishoud Taak Bewerken");
        model.addAttribute("chore", chore.get());
        model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());

        return "add-chore";
    }

    @GetMapping("/delete/{id}")
    public String deleteChore(@PathVariable Long id) {
        log.info("Verwijderverzoek voor taak: {}", id);
        choreService.deleteChoreById(id);
        return "redirect:/chores";
    }

    @PostMapping("/save")
    public String saveChore(
            @Valid @ModelAttribute Chore chore,
            @RequestParam("coverImageFile") MultipartFile coverImageFile,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) throws IOException {

        if (!coverImageFile.isEmpty()) {
            Image image = new Image();
            image.setData(coverImageFile.getBytes());
            image.setContentType(coverImageFile.getContentType());
            imageRepository.save(image);
            chore.setCoverImage(image);
        }

        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}", bindingResult.getErrorCount());
            model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
            return "add-chore";
        }

        choreService.saveChore(chore);

        redirectAttributes.addFlashAttribute("successMessage", "Taak succesvol toegevoegd!");
        return "redirect:/chores";
    }

    @GetMapping({"/{choreName}", "/detail/{choreName}"})
    public String showChoreDetail(
            @PathVariable String choreName, Model model) {
        Optional<Chore> chore = choreService.getChoreByName(choreName);

        if (chore.isEmpty()) {
            return "redirect:/chores";
        }

        model.addAttribute("chore", chore.get());
        return "chore-detail";
    }
}
