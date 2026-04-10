package nl.miwnn.cohort19.mylinh.ChoreManager.service.mapper;

import nl.miwnn.cohort19.mylinh.ChoreManager.dto.NewChoreManagerUserDTO;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author My Linh Lu
 */
@Component
public class ChoreManagerUserMapper {

    public static ChoreManagerUser toChoreManagerUser(
            NewChoreManagerUserDTO dto,
            PasswordEncoder passwordEncoder) {

        ChoreManagerUser user = new ChoreManagerUser();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPlainPassword()));
        user.setRole(dto.getRole());

        return user;
    }
}
