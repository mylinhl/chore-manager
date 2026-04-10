package nl.miwnn.cohort19.mylinh.ChoreManager.dto;

/**
 * @author My Linh Lu
 * Supports new user form
 */
public class NewChoreManagerUserDTO {
    private String username;
    private String plainPassword;
    private String checkPassword;
    private String role;

    public NewChoreManagerUserDTO() {}

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
