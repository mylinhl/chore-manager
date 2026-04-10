package nl.miwnn.cohort19.mylinh.ChoreManager.service.mapper;

import nl.miwnn.cohort19.mylinh.ChoreManager.dto.NewChoreManagerUserDTO;
import nl.miwnn.cohort19.mylinh.ChoreManager.model.ChoreManagerUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author My Linh Lu
 */
class ChoreManagerUserMapperTest {

    private final ChoreManagerUserMapper mapper = new ChoreManagerUserMapper();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void toChoreManagerUser_shouldEncodePassword() {
        // Arrange
        NewChoreManagerUserDTO dto = new NewChoreManagerUserDTO();
        dto.setUsername("testgebruiker");
        dto.setPlainPassword("geheim123");
        dto.setRole("USER");

        // Act
        ChoreManagerUser result = mapper.toChoreManagerUser(dto, encoder);

        // Assert
        assertEquals("testgebruiker", result.getUsername());
        assertEquals("USER",result.getRole());

        assertNotEquals("geheim123",result.getPassword());

        assertTrue(encoder.matches("geheim123",result.getPassword()));
    }

    @Test
    void toChoreManagerUser_shouldSetRoleCorrectly() {
        NewChoreManagerUserDTO dto = new NewChoreManagerUserDTO();
        dto.setUsername("beheerder");
        dto.setPlainPassword("adminpass");
        dto.setRole("ADMIN");

        ChoreManagerUser result = mapper.toChoreManagerUser(dto, encoder);

        assertEquals("ADMIN",result.getRole());
    }


}