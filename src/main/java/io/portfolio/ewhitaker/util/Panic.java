package io.portfolio.ewhitaker.util;

public class Panic extends Error {
    public Panic() {
        super();
    }

    public Panic(String message) {
        super(message);
    }

    public Panic(Throwable cause) {
        super(cause);
    }

    public Panic(String message, Throwable cause) {
        super(message, cause);
    }
}