package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;
import io.portfolio.ewhitaker.utility.Result;
import io.portfolio.ewhitaker.utility.Strings;

public final class File extends FileDescriptor {
    public static Result<File> creat(String pathname, BitSet<FilePermission> mode) {
        String[] path = FileSystem.normalize(pathname);
        String normalized = Strings.join(path, "/");

        int identifier = SystemCall.creat(normalized, mode.value());
        if (identifier < 0) {
            return Result.error(new Panic());
        }
        return Result.ok(new File(normalized, mode, identifier));
    }

    private File(String pathname, BitSet<FilePermission> mode, int identifier) {
        super(pathname, mode, identifier);
    }
}
