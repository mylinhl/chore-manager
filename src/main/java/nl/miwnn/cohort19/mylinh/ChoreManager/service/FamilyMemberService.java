package nl.miwnn.cohort19.mylinh.ChoreManager.service;

import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage business logic for Family Members
 */
@Service
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Transactional(readOnly = true)
    public List<FamilyMember> getAllFamilyMembers() {
        return familyMemberRepository.findAll();
    }

    public FamilyMember saveFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    public Optional<FamilyMember> getFamilyMemberById(Long id) {
        return familyMemberRepository.findById(id);
    }

    public void deleteFamilyMemberById(Long id) {
        familyMemberRepository.deleteById(id);
    }
}
