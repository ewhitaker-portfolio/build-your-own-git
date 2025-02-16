package io.portfolio.ewhitaker.utility;

public final class Assert {
    private Assert() {
        super();
    }

    public static <T> T isEqual(T expected, T actual) {
        return isEqual(expected, actual, null);
    }

    public static <T> T isEqual(T expected, T actual, String message) {
        if ((expected != actual) && (expected == null || !expected.equals(actual))) {
            throw new Panic(equalMessage(expected, actual, message));
        }
        return actual;
    }

    public static <T> T isNotNull(T instance) {
        return isNotNull(instance, "object must not be null");
    }

    public static <T> T isNotNull(T instance, String message) {
        if (instance == null) {
            throw new Panic(message == null ? "object must not be null" : message);
        }
        return instance;
    }

    public static <T> T isInstanceOf(Class<T> target, Object instance) {
        return isInstanceOf(target, instance, null);
    }

    public static <T> T isInstanceOf(Class<T> target, Object instance, String message) {
        if (!isNotNull(target, "target class must not be null").isInstance(instance)) {
            throw new Panic(instanceOfMessage(target, instance, message));
        }
        return target.cast(instance);
    }

    private static String equalMessage(Object expected, Object actual, String message) {
        StringBuilder result = new StringBuilder();
        if (message != null && !message.isBlank()) {
            result.append(message).append(" ==> ");
        }
        result.append("expected: %s, but was: %s".formatted(toString(expected), toString(actual)));
        return result.toString();
    }

    private static String instanceOfMessage(Class<?> type, Object instance, String message) {
        String classname = instance == null ? "null" : instance.getClass().getSimpleName();
        StringBuilder result = new StringBuilder();
        if (message != null && !message.isBlank()) {
            result.append(message.strip());
            if (!endsWithPunctuation(result)) {
                return result + ": " + classname;
            }
            result.append(" ");
        }
        result.append("Object of class [").append(classname).append("] must be an instance of ").append(type);
        return result.toString();

    }

    private static boolean endsWithPunctuation(StringBuilder message) {
        return message.charAt(message.length() - 1) == ','
                || message.charAt(message.length() - 1) == '?'
                || message.charAt(message.length() - 1) == '!'
                || message.charAt(message.length() - 1) == '-'
                || message.charAt(message.length() - 1) == '.'
                || message.charAt(message.length() - 1) == ':'
                || message.charAt(message.length() - 1) == ';';
    }

    private static String toString(Object o) {
        if (o == null) {
            return "<null>";
        }
        return o.toString();
    }
}
