package io.portfolio.ewhitaker.system.io;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;
import io.portfolio.ewhitaker.utility.Result;
import io.portfolio.ewhitaker.utility.Strings;

public sealed abstract class FileDescriptor {
    private final String pathname;
    private final BitSet<FilePermissions> mode;

    private final int identifier;

    private FileDescriptor(String pathname, BitSet<FilePermissions> mode, int identifier) {
        this.pathname = pathname;
        this.mode = mode;
        this.identifier = identifier;
    }

    public static final class File extends FileDescriptor {
        public static Result<File> creat(String pathname, BitSet<FilePermissions> mode) {
            String[] path = FileSystem.normalize(pathname);
            String normalized = Strings.join(path, "/");

            int identifier = SystemCall.creat(normalized, mode.value());
            if (identifier < 0) {
                return Result.error(new Panic());
            }
            return Result.ok(new File(normalized, mode, identifier));
        }

        private File(String pathname, BitSet<FilePermissions> mode, int identifier) {
            super(pathname, mode, identifier);
        }
    }

    public static final class Directory extends FileDescriptor {
        public static Result<Directory> creat(String pathname, BitSet<FilePermissions> mode) {
            String[] path = FileSystem.normalize(pathname);
            String normalized = Strings.join(path, "/");

            int identifier = SystemCall.mkdir(normalized, mode.value());
            if (identifier == -1) {
                return Result.error(new Panic());
            }
            return Result.ok(new Directory(normalized, mode, identifier));
        }

        private Directory(String pathname, BitSet<FilePermissions> mode, int identifier) {
            super(pathname, mode, identifier);
        }
    }
}
