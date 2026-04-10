package nl.miwnn.cohort19.mylinh.ChoreManager.service;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage business logic for chores
 */
@Service
public class ChoreService {

    private static final Logger log = LoggerFactory.getLogger(ChoreService.class);
    private final ChoreRepository choreRepository;


    public ChoreService(ChoreRepository choreRepository) {
        this.choreRepository = choreRepository;
    }

    @Transactional(readOnly = true)
    public List<Chore> getAllChores(String query) {
        if (query != null && !query.isBlank()) {
            return  choreRepository.findChoresByChoreNameContainingIgnoreCase(query);
        }
        return choreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Chore> getChoresByFamilyMemberId(Long memberId) {
        return choreRepository.findByFamilymembers_MemberId(memberId);
    }

    public Chore saveChore(Chore chore) {
        log.info("Nieuwe huishoud taak toegevoegd: {}", chore.getChoreName());
        return choreRepository.save(chore);
    }

    public Optional<Chore> getChoreById(Long id) {
        return choreRepository.findById(id);
    }

    public void deleteChoreById(Long id) {
        choreRepository.deleteById(id);
    }

    public Optional<Chore> getChoreByName(String choreName) {
        return choreRepository.findByChoreName(choreName);
    }
}
