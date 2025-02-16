package io.portfolio.ewhitaker.system.io;

public final class FileSystem {
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String USER_DIRECTORY = System.getProperty("user.dir");

    public static String[] normalize(String pathname) {
        return pathname.split("/");
    }

    private FileSystem() {
        super();
    }

}
