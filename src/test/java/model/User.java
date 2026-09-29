package model;

/**
 * Simple data holder for a test user's credentials and role, used by
 * {@code types.DataTableTypes} to convert Cucumber data table rows (e.g. the
 * {@code username | password | role} table in {@code login.feature}) into
 * {@link User} objects consumed by step definitions.
 */
public class User {

    private String username;
    private String password;
    private String role;

    public User(String username,
                String password,
                String role) {

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
