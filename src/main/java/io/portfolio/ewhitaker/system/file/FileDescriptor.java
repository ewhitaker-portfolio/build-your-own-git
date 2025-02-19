package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;

public sealed abstract class FileDescriptor implements AutoCloseable permits File, Directory {
    private final String pathname;
    private final BitSet<FilePermission> mode;

    private final int identifier;

    public FileDescriptor(String pathname, BitSet<FilePermission> mode, int identifier) {
        this.pathname = pathname;
        this.mode = mode;
        this.identifier = identifier;
    }

    @Override
    public void close() {
        if (SystemCall.close(this.identifier) == -1) {
            throw new Panic();
        }
    }

    public int identifier() {
        return this.identifier;
    }
}
