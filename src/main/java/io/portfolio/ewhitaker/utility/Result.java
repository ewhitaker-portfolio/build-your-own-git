package io.portfolio.ewhitaker.utility;

public sealed interface Result<T> {
    final class Ok<T> implements Result<T> {
        private final T value;

        private Ok(T value) {
            this.value = value;
        }
    }

    static <T> Result<T> ok(T value) {
        return new Ok<>(value);
    }

    final class Error<T> implements Result<T> {
        private final Panic error;

        private Error(Panic error) {
            this.error = error;
        }
    }

    static <T> Result<T> error(Panic error) {
        return new Error<>(error);
    }

    default boolean isOk() {
        return switch (this) {
            case Result.Ok<T> _ -> true;
            case Result.Error<T> _ -> false;
        };
    }

    default boolean isError() {
        return switch (this) {
            case Result.Ok<T> _ -> false;
            case Result.Error<T> _ -> true;
        };
    }

    default T get() {
        return switch (this) {
            case Result.Ok<T> ok -> ok.value;
            case Result.Error<T> err -> throw new Panic("Result#get() called on [Result$Error] variant", err.error);
        };
    }
}
