package nl.miwnn.cohort19.mylinh.ChoreManager.service;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.SubTask;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

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
}
