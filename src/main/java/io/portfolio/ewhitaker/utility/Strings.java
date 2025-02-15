package io.portfolio.ewhitaker.utility;

public final class Strings {
    private Strings() {
        super();
    }

    public static String join(String[] array, String delimiter) {
        if (array.length == 0) {
            return "";
        }

        int len = 0;
        if (array.length > 1) {
            len += delimiter.length() * (array.length - 1);
        }
        for (String s : array) {
            len += s.length();
        }

        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < array.length - 1; ++i) {
            builder.append(array[i]).append(delimiter);
        }
        builder.append(array[array.length - 1]);

        return builder.toString();
    }
}
