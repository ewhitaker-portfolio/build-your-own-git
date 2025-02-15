package io.portfolio.ewhitaker.system;

import io.portfolio.ewhitaker.utility.Strings;

public sealed abstract class FileDescriptor {
    private final String[] path;
    private final String pathname;
    private final int mode;

    private final int identifier;

    @FunctionalInterface
    private interface IdentifierDispatch {
        int dispatch(String pathname, int mode);
    }

    private FileDescriptor(String pathname, int mode, IdentifierDispatch identifier) {
        this.path = FileSystem.normalize(pathname);
        this.pathname = Strings.join(this.path, "/");
        this.mode = mode;
        this.identifier = identifier.dispatch(this.pathname, mode);
    }

    public static final class File extends FileDescriptor {
        public File(String pathname, int mode) {
            super(pathname, mode, SystemCall::creat);
        }
    }

    public static final class Directory extends FileDescriptor {
        public Directory(String pathname, int mode) {
            super(pathname, mode, SystemCall::mkdir);
        }
    }
}
