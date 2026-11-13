package com.allpatekplatform.shared.application.result;

import java.util.Optional;
import java.util.function.Function;

/**
 * Result of a command or query that can succeed with a value or fail with an error.
 *
 * @param <T> successful result value
 * @param <E> error information
 */
public sealed interface Result<T, E> {

    record Success<T, E>(T value) implements Result<T, E> {
    }

    record Failure<T, E>(E error) implements Result<T, E> {
    }

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default Optional<T> toOptional() {
        return switch (this) {
            case Success<T, E> success -> Optional.of(success.value);
            case Failure<T, E> ignored -> Optional.empty();
        };
    }

    default T getOrElse(T defaultValue) {
        return switch (this) {
            case Success<T, E> success -> success.value;
            case Failure<T, E> ignored -> defaultValue;
        };
    }

    default <E2> Result<T, E2> mapError(Function<E, E2> mapper) {
        return switch (this) {
            case Success<T, E> success -> Result.success(success.value);
            case Failure<T, E> failure -> Result.failure(mapper.apply(failure.error));
        };
    }

    default <T2> Result<T2, E> flatMap(Function<T, Result<T2, E>> mapper) {
        return switch (this) {
            case Success<T, E> success -> mapper.apply(success.value);
            case Failure<T, E> failure -> Result.failure(failure.error);
        };
    }

    default <T2> Result<T2, E> map(Function<T, T2> mapper) {
        return switch (this) {
            case Success<T, E> success -> Result.success(mapper.apply(success.value));
            case Failure<T, E> failure -> Result.failure(failure.error);
        };
    }

    default Result<T, E> recover(Function<E, Result<T, E>> recovery) {
        return switch (this) {
            case Success<T, E> ignored -> this;
            case Failure<T, E> failure -> recovery.apply(failure.error);
        };
    }
}
