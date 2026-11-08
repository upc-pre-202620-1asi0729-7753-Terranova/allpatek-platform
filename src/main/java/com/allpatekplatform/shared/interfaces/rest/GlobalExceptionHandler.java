package com.allpatekplatform.shared.interfaces.rest;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Translates unhandled exceptions into RFC 9457 ProblemDetail responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MESSAGES_BASENAME = "messages";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var fieldErrors = exception.getBindingResult().getFieldErrors();
        var validationPrefix = resolveMessageOrDefault("validation.field.prefix", "Field");
        var errorDetails = fieldErrors.isEmpty()
                ? resolveMessageOrDefault("validation.request.failed", "Request validation failed")
                : fieldErrors.stream()
                        .map(error -> "%s %s: %s".formatted(
                                validationPrefix,
                                error.getField(),
                                error.getDefaultMessage()))
                        .reduce((left, right) -> left + "; " + right)
                        .orElse(resolveMessageOrDefault("validation.request.failed", "Request validation failed"));

        var invalidParams = fieldErrors.stream().map(error -> {
            Map<String, String> item = new LinkedHashMap<>();
            item.put("field", error.getField());
            item.put("message", error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value");
            return item;
        }).toList();

        var problemDetail = ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(
                ApplicationError.validationError("request-body", errorDetails));
        if (!invalidParams.isEmpty()) {
            problemDetail.setProperty("invalidParams", invalidParams);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.validationError(
                        resolveMessageOrDefault("validation.request.argument", "request-argument"),
                        exception.getMessage() != null
                                ? exception.getMessage()
                                : resolveMessageOrDefault("validation.request.failed", "Request validation failed")));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.unexpected(
                        resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                        exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception exception) {
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                ApplicationError.unexpected(
                        resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                        exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred"));
    }

    private String resolveMessageOrDefault(String key, String defaultValue, Object... args) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, LocaleContextHolder.getLocale());
            if (!bundle.containsKey(key)) {
                return defaultValue;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ignored) {
            return defaultValue;
        }
    }
}
