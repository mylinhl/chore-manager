package nl.miwnn.cohort19.mylinh.ChoreManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * @author My Linh Lu
 * Control family members who perform chores in the Chore Manager
 */
@Entity
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotBlank(message = "Voornaam mag niet leeg zijn")
    private String firstName;

    @NotBlank(message = "Achternaam mag niet leeg zijn")
    private String lastName;

    public FamilyMember() {}

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
