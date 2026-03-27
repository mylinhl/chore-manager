package nl.miwnn.cohort19.mylinh.ChoreManager.repository;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 */
public interface ChoreRepository extends JpaRepository<Chore, Long> {
    Optional<Chore> findByChoreName(String choreName);

    List<Chore> findChoresByTitleContainingIgnoreCase(String choreName);
}
