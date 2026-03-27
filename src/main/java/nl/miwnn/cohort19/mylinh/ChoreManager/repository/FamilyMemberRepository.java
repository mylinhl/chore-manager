package nl.miwnn.cohort19.mylinh.ChoreManager.repository;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author My Linh Lu
 */
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    Optional<FamilyMember> findByLastNameAndFirstName(String lastName, String firstName);
}
