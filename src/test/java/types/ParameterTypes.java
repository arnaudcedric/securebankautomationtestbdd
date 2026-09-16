package types;

import io.cucumber.java.ParameterType;

public class ParameterTypes {

    @ParameterType("SUCCESS|FAILURE|LOCKED")
    public LoginResult loginResult(String value) {

        return LoginResult.valueOf(value.toUpperCase());
    }

}
