package nl.miwnn.cohort19.mylinh.ChoreManager.service;

import nl.miwnn.cohort19.mylinh.ChoreManager.dto.NewChoreManagerUserDTO;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import nl.miwnn.cohort19.mylinh.ChoreManager.repository.UserRepository;
import nl.miwnn.cohort19.mylinh.ChoreManager.service.mapper.ChoreManagerUserMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author My Linh Lu
 * Handle all businesslogic regarding users
 */
@Service
public class ChoreManagerUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChoreManagerUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("Gebruiker niet gevonden met gebruikersnaam: " + username));
    }

    public void saveNewUser(NewChoreManagerUserDTO dto) {
        ChoreManagerUser choreManagerUser = ChoreManagerUserMapper.toChoreManagerUser(dto, passwordEncoder);
        userRepository.save(choreManagerUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public @Nullable Object getAllUsers() {
        return userRepository.findAll();
    }
}
