package com.allpatekplatform.shared.interfaces.rest.transform;

import com.allpatekplatform.shared.application.result.ApplicationError;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Assembler for transforming {@link ApplicationError} instances into RFC 9457 {@link ProblemDetail} responses.
 */
public final class ProblemDetailFromApplicationErrorAssembler {

    private static final String MESSAGES_BASENAME = "messages";
    private static final String ERROR_TYPE_PREFIX = "/api/v1/errors/";

    private ProblemDetailFromApplicationErrorAssembler() {
    }

    public static ProblemDetail toProblemDetailFromApplicationError(ApplicationError error) {
        HttpStatusCode status = toStatusFromErrorCode(error.code());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, toLocalizedMessageFromApplicationError(error));
        problemDetail.setType(toProblemTypeUri(error.code()));
        problemDetail.setTitle(toProblemTitle(error.code(), status));
        problemDetail.setProperty("code", error.code());
        if (error.details() != null && !error.details().isBlank()) {
            problemDetail.setProperty("details", error.details());
        }
        return problemDetail;
    }

    public static ResponseEntity<ProblemDetail> toResponseFromApplicationError(ApplicationError error) {
        ProblemDetail problemDetail = toProblemDetailFromApplicationError(error);
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }

    public static HttpStatusCode toStatusFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case String code when code.endsWith("_NOT_FOUND") -> HttpStatus.NOT_FOUND;
            case "BUSINESS_RULE_VIOLATION" -> HttpStatusCode.valueOf(422);
            case String code when code.endsWith("_CONFLICT") -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private static URI toProblemTypeUri(String errorCode) {
        String slug = switch (errorCode) {
            case "VALIDATION_ERROR" -> "validation-error";
            case "BUSINESS_RULE_VIOLATION" -> "business-rule-violation";
            case "UNEXPECTED_ERROR" -> "unexpected-error";
            case String code when code.endsWith("_NOT_FOUND") -> "not-found";
            case String code when code.endsWith("_CONFLICT") -> "conflict";
            default -> errorCode.toLowerCase(Locale.ROOT).replace('_', '-');
        };
        return URI.create(ERROR_TYPE_PREFIX + slug);
    }

    private static String toProblemTitle(String errorCode, HttpStatusCode status) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> "Validation Error";
            case "BUSINESS_RULE_VIOLATION" -> "Business Rule Violation";
            case String code when code.endsWith("_NOT_FOUND") -> "Resource Not Found";
            case String code when code.endsWith("_CONFLICT") -> "Resource Conflict";
            default -> status instanceof HttpStatus httpStatus ? httpStatus.getReasonPhrase() : "Error";
        };
    }

    private static String toLocalizedMessageFromApplicationError(ApplicationError error) {
        String specificKey = toSpecificMessageKeyFromErrorCode(error.code());
        String specificMessage = toLocalizedMessageOrNull(
                specificKey,
                error.details(),
                toEntityNameFromErrorCode(error.code()));
        if (specificMessage != null) {
            return specificMessage;
        }

        String fallbackKey = toMessageKeyFromErrorCode(error.code());
        return toLocalizedMessageWithFallback(
                fallbackKey,
                error.message(),
                error.details(),
                toEntityNameFromErrorCode(error.code()));
    }

    private static String toSpecificMessageKeyFromErrorCode(String errorCode) {
        return "error.%s.message".formatted(errorCode.toLowerCase(Locale.ROOT).replace('_', '-'));
    }

    private static String toMessageKeyFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> "error.validation.message";
            case "BUSINESS_RULE_VIOLATION" -> "error.business-rule.message";
            case "UNEXPECTED_ERROR" -> "error.unexpected.message";
            case String code when code.endsWith("_NOT_FOUND") -> "error.not-found.message";
            case String code when code.endsWith("_CONFLICT") -> "error.conflict.message";
            default -> "error.generic.message";
        };
    }

    private static String toEntityNameFromErrorCode(String errorCode) {
        if (errorCode.endsWith("_NOT_FOUND")) {
            return errorCode.replace("_NOT_FOUND", "").toLowerCase(Locale.ROOT);
        }
        if (errorCode.endsWith("_CONFLICT")) {
            return errorCode.replace("_CONFLICT", "").toLowerCase(Locale.ROOT);
        }
        return "resource";
    }

    private static String toLocalizedMessageOrNull(String key, Object... args) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, LocaleContextHolder.getLocale());
            if (!bundle.containsKey(key)) {
                return null;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ignored) {
            return null;
        }
    }

    private static String toLocalizedMessageWithFallback(String key, String fallback, Object... args) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, LocaleContextHolder.getLocale());
            if (!bundle.containsKey(key)) {
                return fallback;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ignored) {
            return fallback;
        }
    }
}
