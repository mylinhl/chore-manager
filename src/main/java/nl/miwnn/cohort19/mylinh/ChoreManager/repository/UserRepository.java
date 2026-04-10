package nl.miwnn.cohort19.mylinh.ChoreManager.repository;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author My Linh Lu
 */
public interface UserRepository extends JpaRepository<ChoreManagerUser, Long> {
    Optional<ChoreManagerUser> findByUsername(String username);
}
