package io.portfolio.ewhitaker.system;

public final class FileSystem {
    private FileSystem() {
        super();
    }

    public static String[] normalize(String pathname) {
        return pathname.split("/");
    }
}
