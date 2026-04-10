package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.SubTask;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
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
    private final SubTaskRepository subTaskRepository;
    private final ChoreRepository choreRepository;
    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskRepository subTaskRepository, ChoreRepository choreRepository, SubTaskService subTaskService) {
        this.subTaskRepository = subTaskRepository;
        this.choreRepository = choreRepository;
        this.subTaskService = subTaskService;
    }

    @GetMapping("/add")
    public String addChoreForm(Model model) {
        log.debug("Formulier voor nieuwe subtaak opgevraagd.");
        model.addAttribute("paginaTitel", "Subtaak aan Huishoud Taak Toevoegen");
        model.addAttribute("subtask", new SubTask());
        model.addAttribute("allSubtasks", subTaskService.getAllSubTasks());
        model.addAttribute("allChores", choreRepository.findAll());
        return "add-subtask";
    }

    @PostMapping("/add")
    public String processAddChore(@ModelAttribute SubTask subtask) {
        log.info("Nieuwe subtaak toegevoegd: {}", subtask.getSubtaskName());
        subTaskRepository.save(subtask);
        return "redirect:/chores";
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
            model.addAttribute("allSubtasks", subTaskRepository.findAll());
            return "add-subtask";
        }

        subTaskRepository.save(subtask);
        log.info("Subtaak opgeslagen: {}", subtask.getSubtaskName());

        redirectAttributes.addFlashAttribute("successMessage", "Subtaak succesvol toegevoegd!");
        return "redirect:/chores";
    }

    @PostMapping("/check/{subtaskId}")
    public String checkSubTask(@PathVariable Long subtaskId, String choreName) {
        subTaskRepository.findById(subtaskId).ifPresent(subTask -> {
            if (subTask.getFinished()) {
                subTask.setFinished(false);
                subTaskRepository.save(subTask);
            }
        });
        return "redirect:/chores" + choreName;
    }

    @PostMapping("/uncheck/{id}")
    public String uncheckSubTask(@PathVariable Long id, String choreName) {
        subTaskRepository.findById(id).ifPresent(subtask -> {
            if (!subtask.getFinished()) {
                subtask.setFinished(true);
                subTaskRepository.save(subtask);
            }
        });
        return "redirect:/chores" + choreName;
    }
}
