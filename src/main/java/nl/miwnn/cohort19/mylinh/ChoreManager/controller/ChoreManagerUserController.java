package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import nl.miwnn.cohort19.mylinh.ChoreManager.dto.NewChoreManagerUserDTO;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.UserRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.mapper.ChoreManagerUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author My Linh Lu
 * Manage elements for users in Chore Manager
 */
@Controller
@RequestMapping("/users")
public class ChoreManagerUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChoreManagerUserController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"", "/"})
    public String showUserOverview(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newUser", new NewChoreManagerUserDTO());
        return "user-overview";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("newUser", new NewChoreManagerUserDTO());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(
            @ModelAttribute("newUser") NewChoreManagerUserDTO dto,
            RedirectAttributes redirectAttributes) {

        userRepository.save(ChoreManagerUserMapper.toChoreManagerUser(dto, passwordEncoder));
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Gebruiker '" + dto.getUsername() + "' aangemaakt.");
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Gebruiker verwijderd.");
        return "redirect:/users";
    }
}
