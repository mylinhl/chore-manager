package nl.miwnn.cohort19.mylinh.ChoreManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.awt.print.Book;

/**
 * @author My Linh Lu
 * Smaller tasks that form a whole chore together
 */
@Entity
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subtaskId;

    @NotBlank(message = "Naam voor subtaak mag niet leeg zijn.")
    @Size(max = 200, message = "Naam voor subtaak mag maximaal 100 tekens bevatten")
    private String subtaskName;

    @ManyToOne
    @JoinColumn(name = "chore_id")
    private Chore chore;

    private boolean finished;

    public SubTask(Chore chore, String subtaskName) {
        this.chore = chore;
        this.subtaskName = subtaskName;
        this.finished = true;
    }

    public SubTask() {}

    public Long getSubtaskId() {
        return subtaskId;
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public void setId(Long subtaskId) {
        this.subtaskId = subtaskId;
    }

    public Chore getChore() {
        return chore;
    }

    public void setChore(Chore chore) {
        this.chore = chore;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
