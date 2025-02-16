package io.portfolio.ewhitaker.system;

import io.portfolio.ewhitaker.system.file.FileStatus;

public final class SystemCall {
    static {
        System.loadLibrary("build-your-own-git");
    }

    private SystemCall() {
        super();
    }

    public static native int stat(String pathname, FileStatus statbuf);

    public static native int creat(String pathname, long mode);

    public static native int mkdir(String pathname, long mode);

    private static native void init();

    static {
        init();
    }
}
