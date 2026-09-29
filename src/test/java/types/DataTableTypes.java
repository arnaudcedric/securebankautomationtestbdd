package types;

import io.cucumber.java.DataTableType;
import model.User;

import java.util.Map;

/**
 * Cucumber {@link DataTableType} conversions. {@link #userEntry(Map)} maps a
 * single data table row (with {@code username}/{@code password}/{@code role}
 * columns, as used in {@code login.feature}) into a {@link User} object so
 * step definitions can receive typed {@code List<User>} arguments instead of
 * raw table data.
 */
public class DataTableTypes {

    @DataTableType
    public User userEntry(Map<String, String> entry) {

        return new User(
                entry.get("username"),
                entry.get("password"),
                entry.get("role")
        );
    }

}
