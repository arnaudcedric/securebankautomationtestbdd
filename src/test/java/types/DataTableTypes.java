package types;

import io.cucumber.java.DataTableType;
import model.User;

import java.util.Map;

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
