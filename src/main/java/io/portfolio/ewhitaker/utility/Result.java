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
        private final Throwable error;

        private Error(Throwable error) {
            this.error = error;
        }
    }

    static <T> Result<T> error(Throwable error) {
        return new Error<>(error);
    }

    default T get() {
        return switch (this) {
            case Result.Ok<T> ok -> ok.value;
            case Result.Error<T> t -> throw new Panic("Result#get() called on [Result$Error] variant", t.error);
        };
    }
}
