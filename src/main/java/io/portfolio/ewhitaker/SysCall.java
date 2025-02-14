package io.portfolio.ewhitaker;

public class SysCall {
    static {
        System.loadLibrary("build-your-own-git");
    }

    public static native void hello();
}
