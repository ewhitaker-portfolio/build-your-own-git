package io.portfolio.ewhitaker.util;

public final class Assert {
    private static final boolean ENABLED = Boolean.parseBoolean(System.getProperty("custom.assert.enabled"));

    private Assert() {
        super();
    }

    public static <T> T isNotNull(T instance) {
        if (ENABLED && instance == null) {
            throw new Panic("object must not be null");
        }
        return instance;
    }

    public static <T> T isNotNull(T instance, String message) {
        if (ENABLED && instance == null) {
            throw new Panic(message == null ? "object must not be null" : message);
        }
        return instance;
    }

    public static <T> T isInstanceOf(Class<T> target, Object instance) {
        if (ENABLED && !isNotNull(target, "target class must not be null").isInstance(instance)) {
            throw new Panic(findInstanceOfMessage(target, instance, null));
        }
        return target.cast(instance);
    }

    public static <T> T isInstanceOf(Class<T> target, Object instance, String message) {
        if (ENABLED && !isNotNull(target, "target class must not be null").isInstance(instance)) {
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
        return result.append("Object of class [").append(classname).append("] must be an instance of ").append(type).toString();
    }

    private static boolean endsWithPunctuation(StringBuilder message) {
        return message.charAt(message.length() - 1) == ',' || message.charAt(message.length() - 1) == '?' || message.charAt(message.length() - 1) == '!' || message.charAt(message.length() - 1) == '-' || message.charAt(message.length() - 1) == '.' || message.charAt(message.length() - 1) == ':' || message.charAt(message.length() - 1) == ';';
    }
}