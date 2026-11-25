package com.allpatekplatform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource received to authenticate an existing user.
 */
@Schema(
    name = "SignInRequest",
    description = "User sign-in request with credentials",
    example = "{\"username\": \"gustavo.suyo@allpatek.demo\", \"password\": \"Allpatek2026!\"}"
)
public record SignInResource(
    @Schema(
        description = "Account email used as username",
        example = "gustavo.suyo@allpatek.demo",
        minLength = 3,
        maxLength = 160
    )
    String username,

    @Schema(
        description = "User password",
        example = "SecurePass123!",
        minLength = 8,
        maxLength = 255
    )
    String password
) {
}
