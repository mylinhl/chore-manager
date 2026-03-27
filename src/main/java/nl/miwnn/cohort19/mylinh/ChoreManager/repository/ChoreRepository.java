package nl.miwnn.cohort19.mylinh.ChoreManager.repository;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author My Linh Lu
 */
public interface ChoreRepository extends JpaRepository<Chore, Long> {
}
