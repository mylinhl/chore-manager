package nl.miwnn.cohort19.mylinh.ChoreManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author My Linh Lu
 * Control chores managed in the Chore Manager
 */
@Entity
public class Chore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Naam voor huishoud taak mag niet leeg zijn.")
    @Size(max = 100, message = "Naam voor huishoud taak mag maximaal 100 tekens bevatten")
    private String choreName;

    @NotNull(message = "Frequentie per week is verplicht")
    @Min(value = 1, message = "Frequentie moet minimaal 1 keer per week zijn")
    @Max(value = 7, message = "Frequentie mag maximaal 7 keer per week zijn")
    private Integer choreFrequency;

    @NotBlank(message = "Locatie mag niet leeg zijn")
    private String location;

    @NotBlank(message = "Verantwoordelijke mag niet leeg zijn")
    private String responsibility;

    @OneToMany(mappedBy = "chore", cascade = CascadeType.ALL)
    private List<SubTask> subtasks = new ArrayList<>();

    public Chore(String choreName, int choreFrequency, String location, String responsibility) {
        this.choreName = choreName;
        this.choreFrequency = choreFrequency;
        this.location = location;
        this.responsibility = responsibility;
    }

    public Chore() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChoreFrequency() {
        return choreFrequency;
    }

    public void setChoreFrequency(Integer choreFrequency) {
        this.choreFrequency = choreFrequency;
    }

    public String getChoreName() {
        return choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
