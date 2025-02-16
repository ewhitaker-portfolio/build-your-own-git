package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.BitSet;

public sealed abstract class FileDescriptor permits File, Directory {
    private final String pathname;
    private final BitSet<FilePermission> mode;

    private final int identifier;

    public FileDescriptor(String pathname, BitSet<FilePermission> mode, int identifier) {
        this.pathname = pathname;
        this.mode = mode;
        this.identifier = identifier;
    }
}
