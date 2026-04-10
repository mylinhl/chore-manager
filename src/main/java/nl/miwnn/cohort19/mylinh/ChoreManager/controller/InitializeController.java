package nl.miwnn.cohort19.mylinh.ChoreManager.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.Chore;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.FamilyMember;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.SubTask;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.ChoreRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.FamilyMemberRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.SubTaskRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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
@Component
public class InitializeController {

    private final ChoreRepository choreRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final SubTaskRepository subTaskRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(InitializeController.class);

    public InitializeController(
            ChoreRepository choreRepository,
            FamilyMemberRepository familyMemberRepository, SubTaskRepository subTaskRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.choreRepository = choreRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.subTaskRepository = subTaskRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() throws Exception {
        if (familyMemberRepository.count() == 0) {
            seedFamilyMembers();
        }
        if (choreRepository.count() == 0) {
            seedChores();
        }
        if (userRepository.count() == 0) {
            ChoreManagerUser admin = new ChoreManagerUser(
                    "admin",
                    passwordEncoder.encode("geheim123"),
                    "ADMIN"
            );
            userRepository.save(admin);
        }
        if (subTaskRepository.count() == 0) {
            seedSubTasks();
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

    private void seedSubTasks() throws Exception {
        List<Chore> chores = choreRepository.findAll();

        for (Chore chore : chores) {
            if (chore.getChoreName().equalsIgnoreCase("Vaatwasser draaien")) {
                subTaskRepository.save(new SubTask(chore, "Bestek sorteren"));
                subTaskRepository.save(new SubTask(chore, "Borden stapelen"));
                subTaskRepository.save(new SubTask(chore, "Glazen schoonmaken"));
            }

            if (chore.getChoreName().equalsIgnoreCase("WC schoonmaken")) {
                subTaskRepository.save(new SubTask(chore, "WC reiniger aanbrengen"));
                subTaskRepository.save(new SubTask(chore, "WC reiniger in schrobben"));
                subTaskRepository.save(new SubTask(chore, "WC reiniger 5 min laten inwerken"));
                subTaskRepository.save(new SubTask(chore, "Doorspoelen en na schrobben"));
            }
        }
    }
}
