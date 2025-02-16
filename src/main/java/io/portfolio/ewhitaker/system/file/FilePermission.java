package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.Flag;

public enum FilePermission implements Flag {
    USER_ALL(0700),
    USER_READ(0400),
    USER_WRITE(0200),
    USER_EXECUTE(0100),
    GROUP_ALL(0070),
    GROUP_READ(0040),
    GROUP_WRITE(0020),
    GROUP_EXECUTE(0010),
    OTHER_ALL(0007),
    OTHER_READ(0004),
    OTHER_WRITE(0002),
    OTHER_EXECUTE(0001);

    private final int value;

    FilePermission(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
