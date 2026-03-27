package nl.miwnn.cohort19.mylinh.ChoreManager.model;

import jakarta.persistence.*;

import java.awt.print.Book;

/**
 * @author My Linh Lu
 * Smaller tasks that form a whole chore together
 */
@Entity
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chore_id")
    private Chore chore;

    private boolean finished;

    public SubTask(Chore chore, boolean finished) {
        this.chore = chore;
        this.finished = finished;
    }

    public SubTask() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chore getChore() {
        return chore;
    }

    public void setChore(Chore chore) {
        this.chore = chore;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
