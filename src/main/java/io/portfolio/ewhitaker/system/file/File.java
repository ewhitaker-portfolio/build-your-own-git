package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;
import io.portfolio.ewhitaker.utility.Result;

public final class File extends FileDescriptor {
    public static Result<File> creat(String pathname, BitSet<FilePermission> mode) {
        String normalized = FileSystem.normalize(pathname);

        int identifier = SystemCall.creat(normalized, mode.value());
        if (identifier < 0) {
            return Result.error(new Panic());
        }
        return Result.ok(new File(normalized, mode, identifier));
    }

    public File(String pathname, BitSet<FilePermission> mode, int identifier) {
        super(pathname, mode, identifier);
    }
}
