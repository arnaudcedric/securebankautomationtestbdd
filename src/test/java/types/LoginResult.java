package types;

/**
 * Possible outcomes of a login attempt, used in {@code login.feature}
 * scenario outline steps (e.g. {@code the login result should be SUCCESS
 * with message "..."}) together with {@link ParameterTypes#loginResult}.
 */
public enum LoginResult {

    SUCCESS,
    FAILURE,
    LOCKED

}
