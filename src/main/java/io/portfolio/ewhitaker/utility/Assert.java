package io.portfolio.ewhitaker.utility;

public final class Assert {
    private Assert() {
        super();
    }

    // TODO: better error message
    public static <T> T isEqual(T expected, T actual, String message) {
        if ((expected != actual) && (expected == null || !expected.equals(actual))) {
            throw new Panic(message == null ? "expected %s: actual %s".formatted(expected, actual) : message);
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
            throw new Panic(findInstanceOfMessage(target, instance, message));
        }
        return target.cast(instance);
    }

    private static String findInstanceOfMessage(Class<?> type, Object instance, String message) {
        String classname = instance == null ? "null" : instance.getClass().getSimpleName();
        StringBuilder result = new StringBuilder();
        if (message != null && !message.isBlank()) {
            result.append(message.strip());
            if (!endsWithPunctuation(result)) {
                return result + ": " + classname;
            }
            result.append(" ");
        }
        return result.append("Object of class [").append(classname).append("] must be an instance of ")
                .append(type).toString();
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
}
