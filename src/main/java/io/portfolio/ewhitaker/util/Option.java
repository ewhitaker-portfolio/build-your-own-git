package io.portfolio.ewhitaker.util;

public sealed interface Option<T> {
    final class Some<T> implements Option<T> {
        private final T value;

        private Some(T value) {
            this.value = value;
        }
    }

    static <T> Option<T> of(T value) {
        return new Some<>(value);
    }

    Option<?> NONE = new None<>();

    final class None<T> implements Option<T> {
        private None() {
            super();
        }
    }

    static <T> Option<T> none() {
        return (Option<T>) NONE;
    }

    default T get() {
        return switch (this) {
            case Some<T> s -> s.value;
            case None<T> _ -> throw new Panic("Option#get() called on [Option$None] variant");
        };
    }
}
