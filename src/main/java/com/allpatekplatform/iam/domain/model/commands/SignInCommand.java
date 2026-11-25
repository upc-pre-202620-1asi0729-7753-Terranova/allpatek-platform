package com.allpatekplatform.iam.domain.model.commands;

/**
 * Sign in command
 * <p>
 *     This class represents the command to sign in a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see com.acme.center.platform.iam.domain.model.aggregates.User
 */
public record SignInCommand(String username, String password) {
    public SignInCommand {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("validation.user.username.blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("validation.user.password.blank");
        }
    }
}
