package types;

import io.cucumber.java.ParameterType;

/**
 * Cucumber {@link ParameterType} definitions for custom Gherkin step
 * placeholders. {@link #loginResult(String)} lets step text reference
 * {@code SUCCESS}, {@code FAILURE}, or {@code LOCKED} directly (e.g.
 * {@code the login result should be {loginResult} with message "..."}),
 * converting the matched text into a {@link LoginResult} enum value.
 */
public class ParameterTypes {

    @ParameterType("SUCCESS|FAILURE|LOCKED")
    public LoginResult loginResult(String value) {

        return LoginResult.valueOf(value.toUpperCase());
    }

}
