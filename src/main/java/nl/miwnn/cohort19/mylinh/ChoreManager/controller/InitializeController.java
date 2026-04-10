package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

/**
 * @author My Linh Lu
 * Manage data when initializing app
 */
@Controller
public class InitializeController {

    private final ChoreRepository choreRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Logger log = (Logger) LoggerFactory.getLogger(InitializeController.class);

    public InitializeController(
            ChoreRepository choreRepository,
            FamilyMemberRepository familyMemberRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.choreRepository = choreRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if (familyMemberRepository.count() == 0) {
            seedFamilyMembers();
        }
        if (choreRepository.count() == 0) {
            seedChores();
        }
        if (userRepository.count() == 0) {
            String password = UUID.randomUUID().toString();

            log.info("=============================================");
            log.info("Generated password for 'admin': {}", password);
            log.info("=============================================");
            ChoreManagerUser admin = new ChoreManagerUser(
                    "admin",
                    passwordEncoder.encode(password),
                    true);
            userRepository.save(admin);
        }
    }

    private void seedFamilyMembers() {
        try {
            ClassPathResource resource =
                    new ClassPathResource("seedData/familymembers.csv");
            Reader reader = new InputStreamReader(
                    resource.getInputStream());
            CsvToBean<FamilyMember> csvToBean =
                    new CsvToBeanBuilder<FamilyMember>(reader)
                            .withType(FamilyMember.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            familyMemberRepository.saveAll(csvToBean.parse());
        } catch (IOException e) {
            throw new RuntimeException(
                    "Kon familymembers.csv niet inlezen", e);
        }
    }

    private void seedChores() {
        try {
            ClassPathResource resource =
                    new ClassPathResource("seedData/chores.csv");
            Reader reader = new InputStreamReader(
                    resource.getInputStream());
            CsvToBean<Chore> csvToBean =
                    new CsvToBeanBuilder<Chore>(reader)
                            .withType(Chore.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            List<Chore> chores = csvToBean.parse();
            List<FamilyMember> familyMembers = familyMemberRepository.findAll();
            for (int i = 0; i < chores.size(); i++) {
                Chore chore = chores.get(i);
                chore.getFamilymembers().add(familyMembers.get(i % familyMembers.size()));
                choreRepository.save(chore);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Kon chores.csv niet inlezen", e);
        }
    }
}
