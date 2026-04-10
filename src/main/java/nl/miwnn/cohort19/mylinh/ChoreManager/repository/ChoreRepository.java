package nl.miwnn.cohort19.mylinh.ChoreManager.repository;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 */
public interface ChoreRepository extends JpaRepository<Chore, Long> {
    Optional<Chore> findByChoreName(String choreName);

    List<Chore> findChoresByChoreNameContainingIgnoreCase(String choreName);

    List<Chore> findByFamilymembers_MemberId(Long memberId);
}
