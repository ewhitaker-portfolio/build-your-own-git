package io.portfolio.ewhitaker.util;

public sealed interface Optional<T> {
    final class Some<T> implements Optional<T> {
        private final T value;

        private Some(T value) {
            this.value = value;
        }
    }

    static <T> Optional<T> of(T value) {
        return new Some<>(value);
    }

    Optional<?> NONE = new None<>();

    final class None<T> implements Optional<T> {
        private None() {
            super();
        }
    }

    static <T> Optional<T> none() {
        return (Optional<T>) NONE;
    }

    default T get() {
        return switch (this) {
            case Some<T> s -> s.value;
            case None<T> ignore -> throw new Panic("Optional#get() called on [Optional$None] variant");
        };
    }
}