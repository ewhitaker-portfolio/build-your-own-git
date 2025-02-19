package io.portfolio.ewhitaker.system;

import io.portfolio.ewhitaker.system.file.DirectoryEntry;
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

    public static native int close(int fd);

    public static native FileStatistic stat(String pathname);

    public static native FileStatistic fstat(int fd);

    public static native DirectoryEntry[] getdents(int fd, long count);

    public static native int mkdir(String pathname, long mode);

    public static native int creat(String pathname, long mode);

    private static native void init();

    static {
        init();
    }
}
