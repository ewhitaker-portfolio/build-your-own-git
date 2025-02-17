package io.portfolio.ewhitaker.system;

import io.portfolio.ewhitaker.system.file.FileStatistic;

public final class SystemCall {
    static {
        System.loadLibrary("build-your-own-git");
    }

    private SystemCall() {
        super();
    }

    public static native int open(String pathname, int flags);

    public static native int open(String pathname, int flags, long mode);

    public static native int stat(String pathname, FileStatistic statbuf);

    public static native int fstat(int fd, FileStatistic statbuf);

    public static native int creat(String pathname, long mode);

    public static native int mkdir(String pathname, long mode);

    private static native void init();

    static {
        init();
    }
}
