package io.portfolio.ewhitaker.system;

public final class SystemCall {
    static {
        System.loadLibrary("build-your-own-git");
    }

    private SystemCall() {
        super();
    }

    public static native int creat(String pathname, int mode);

    public static native int mkdir(String pathname, int mode);
}
