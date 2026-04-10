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
    @Column(unique = true)
    private String choreName;

    @NotNull(message = "Frequentie per week is verplicht")
    @Min(value = 1, message = "Frequentie moet minimaal 1 keer per week zijn")
    @Max(value = 7, message = "Frequentie mag maximaal 7 keer per week zijn")
    private Integer choreFrequency;

    @NotBlank(message = "Locatie mag niet leeg zijn")
    private String location;

    @OneToMany(mappedBy = "chore", cascade = CascadeType.ALL)
    private List<SubTask> subtasks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "chore_familymember",
            joinColumns = @JoinColumn(name = "chore_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )

    private List<FamilyMember> familymembers = new ArrayList<>();

    @Column(nullable = true, length = 2000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Chore(String choreName, int choreFrequency, String location) {
        this.choreName = choreName;
        this.choreFrequency = choreFrequency;
        this.location = location;
    }

    public Chore() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
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

    public List<FamilyMember> getFamilymembers() {
        return familymembers;
    }

    public void setFamilymembers(List<FamilyMember> familymembers) {
        this.familymembers = familymembers;
    }
}
