package nl.miwnn.cohort19.mylinh.ChoreManager.service;

import jakarta.transaction.Transactional;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.SubTask;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

/**
 * @author My Linh Lu
 * Manage all business logic for subtask
 */
@Service
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;

    public SubTaskService(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    public List<SubTask> getAllSubTasks() {
        return subTaskRepository.findAll();
    }

    public SubTask createSubTask(SubTask subTask) {
        return subTaskRepository.save(subTask);
    }

   @Transactional
    public void toggleSubTaskFinished(Long subtaskId) {
        SubTask subTask = subTaskRepository.findById(subtaskId)
                .orElseThrow(() -> new IllegalArgumentException("Subtaak niet gevonden"));

        subTask.setFinished((!subTask.getFinished()));
        subTaskRepository.save(subTask);
    }

}
