package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.SubTask;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.ChoreService;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.SubTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.standard.expression.SubtractionExpression;

/**
 * @author My Linh Lu
 * Manage elements for subtasks
 */
@Controller
@RequestMapping("/subtasks")
public class SubTaskController {

    private static final Logger log = LoggerFactory.getLogger(SubTaskController.class);
    private final ChoreService choreService;
    private final SubTaskService subTaskService;

    public SubTaskController(ChoreService choreService, SubTaskService subTaskService) {
        this.choreService = choreService;
        this.subTaskService = subTaskService;
    }

    @GetMapping("/add")
    public String addChoreForm(Model model) {
        log.debug("Formulier voor nieuwe subtaak opgevraagd.");
        model.addAttribute("paginaTitel", "Subtaak aan Huishoud Taak Toevoegen");
        model.addAttribute("subtask", new SubTask());
        model.addAttribute("allSubtasks", subTaskService.getAllSubTasks());
        model.addAttribute("allChores", choreService.getAllChores(null));
        return "add-subtask";
    }

    @PostMapping("/save")
    public String saveChore(
            @Valid @ModelAttribute SubTask subtask,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("Taak opslaan: {}", subtask.getSubtaskName());

        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}", bindingResult.getErrorCount());
            model.addAttribute("allSubtasks", subTaskService.getAllSubTasks());
            return "add-subtask";
        }

        subTaskService.createSubTask(subtask);
        log.info("Subtaak opgeslagen: {}", subtask.getSubtaskName());
        redirectAttributes.addFlashAttribute("successMessage", "Subtaak succesvol toegevoegd!");
        return "redirect:/chores";
    }

    @PostMapping("/toggle/{id}")
    public String toggleSubTask(@PathVariable Long id,
                                @RequestParam String choreName) {

        subTaskService.toggleSubTaskFinished(id);

        return "redirect:/chores/" + choreName;
    }
}
