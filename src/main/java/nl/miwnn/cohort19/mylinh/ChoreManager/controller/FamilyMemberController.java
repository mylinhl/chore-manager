package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author My Linh Lu
 * Manage elements for family member page
 * */
@Controller
@RequestMapping("/familymembers")
public class FamilyMemberController {

    private static final Logger log = LoggerFactory.getLogger(FamilyMemberController.class);
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberController(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @GetMapping("/all")
    public String showOverviewWithForm(Model model) {
        log.debug("Familie leden overzicht opgevraagd");
        model.addAttribute("allFamilyMembers", familyMemberRepository.findAll());
        model.addAttribute("newFamilyMember", new FamilyMember());
        model.addAttribute("activePage", "chores");

        return "family-members";
    }

    @PostMapping("/save")
    public String saveFamilyMember(
            @Valid @ModelAttribute("newFamilyMember") FamilyMember familyMember,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        log.info("Familie Lid opslaan: {}", familyMember.getFullName());

        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}", bindingResult.getErrorCount());
            return "family-members";
        }

        familyMemberRepository.save(familyMember);
        log.info("Nieuw familie lid toegevoegd: {}", familyMember.getFullName());
        redirectAttributes.addFlashAttribute("successMessage", "Familie lid succesvol opgeslagen");
        return "redirect:/familymembers/all";
    }
}
