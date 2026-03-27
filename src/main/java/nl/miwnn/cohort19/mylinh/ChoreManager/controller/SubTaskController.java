package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author My Linh Lu
 * Manage elements for subtasks
 */
@Controller
@RequestMapping("/subtasks")
public class SubTaskController {

    private final SubTaskRepository subTaskRepository;

    public SubTaskController(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @PostMapping("/check/{id}")
    public String checkSubTask(@PathVariable Long id) {
        subTaskRepository.findById(id).ifPresent(subTask -> {
            if (subTask.isFinished()) {
                subTask.setFinished(false);
                subTaskRepository.save(subTask);
            }
        });
        return "redirect:/chores";
    }

    @PostMapping("/uncheck/{id}")
    public String uncheckSubTask(@PathVariable Long id) {
        subTaskRepository.findById(id).ifPresent(subtask -> {
            if (!subtask.isFinished()) {
                subtask.setFinished(true);
                subTaskRepository.save(subtask);
            }
        });
        return "redirect:/chores";
    }
}
