package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Image;
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
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage elements for family member page
 * */
@Controller
@RequestMapping("/familymembers")
public class FamilyMemberController {

    private static final Logger log = LoggerFactory.getLogger(FamilyMemberController.class);;
    private final FamilyMemberService familyMemberService;
    private final ChoreService choreService;
    private final ImageRepository imageRepository;

    public FamilyMemberController(
            FamilyMemberService familyMemberService,
            ChoreService choreService,
            ImageRepository imageRepository) {
        this.familyMemberService = familyMemberService;
        this.choreService = choreService;
        this.imageRepository = imageRepository;
    }

    @GetMapping({"", "/","/all"})
    public String showOverviewWithForm(Model model) {
        log.debug("Familie leden overzicht opgevraagd");
        model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
        model.addAttribute("newFamilyMember", new FamilyMember());
        model.addAttribute("activePage", "family-members");

        return "family-members";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FamilyMember familyMember = familyMemberService.getFamilyMemberById(id)
                .orElseThrow(() -> new IllegalArgumentException("Familielid niet gevonden: " + id));
        model.addAttribute("newFamilyMember", familyMember);
        model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
        model.addAttribute("paginaTitel", "Familielid Bewerken");
        model.addAttribute("activePage", "family-members");
        return "family-members";
    }

    @GetMapping("/delete/{id}")
    public String deleteChore(@PathVariable Long id) {
        log.info("Verwijderverzoek voor taak: {}", id);
        familyMemberService.deleteFamilyMemberById(id);
        return "redirect:/familymembers/all";
    }

    @GetMapping("/detail/{id}")
    public String showFamilyMemberDetail(@PathVariable Long id, Model model){
        Optional<FamilyMember> familyMember = familyMemberService.getFamilyMemberById(id);

        if (familyMember.isEmpty()) {
            return "redirect:/chores";
        }

        model.addAttribute("activePage", "family-members");
        model.addAttribute("familyMember", familyMember.get());
        model.addAttribute("familyMemberChores", choreService.getChoresByFamilyMemberId(id));
        return "family-member-detail";
    }

    @PostMapping("/save")
    public String saveFamilyMember(
            @Valid @ModelAttribute("newFamilyMember") FamilyMember newFamilyMember,
            BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        log.debug("opslaan");
        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}", bindingResult.getErrorCount());
            log.debug("{}", bindingResult.getAllErrors());
            model.addAttribute("allFamilyMembers", familyMemberService.getAllFamilyMembers());
            model.addAttribute("activePage", "family-members");
            return "family-members";
        }

        // Handle image upload separately
        if (!imageFile.isEmpty()) {
            log.debug("image is niet leeg: {}", imageFile);
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType(imageFile.getContentType());
            imageRepository.save(image);
            newFamilyMember.setImage(image); // associate uploaded image
        } else if (newFamilyMember.getMemberId() != null) {
            // Editing existing member, keep old image
            FamilyMember existing = familyMemberService
                    .getFamilyMemberById(newFamilyMember.getMemberId())
                    .orElseThrow();
            newFamilyMember.setImage(existing.getImage());
        }

        familyMemberService.saveFamilyMember(newFamilyMember);

        log.info("Familie lid opgeslagen: {}", newFamilyMember.getFullName());

        redirectAttributes.addFlashAttribute("successMessage", "Familie lid succesvol opgeslagen");

        return "redirect:/familymembers";
    }
}
