package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SubTaskController.class);
    private final SubTaskRepository subTaskRepository;

    public SubTaskController(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    @PostMapping("/check/{subtaskId}")
    public String checkSubTask(@PathVariable Long subtaskId) {
        subTaskRepository.findById(subtaskId).ifPresent(subTask -> {
            if (subTask.getFinished()) {
                subTask.setFinished(false);
                subTaskRepository.save(subTask);
            }
        });
        return "redirect:/chores";
    }

    @PostMapping("/uncheck/{id}")
    public String uncheckSubTask(@PathVariable Long id) {
        subTaskRepository.findById(id).ifPresent(subtask -> {
            if (!subtask.getFinished()) {
                subtask.setFinished(true);
                subTaskRepository.save(subtask);
            }
        });
        return "redirect:/chores";
    }
}
