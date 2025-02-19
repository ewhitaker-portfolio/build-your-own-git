package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;
import io.portfolio.ewhitaker.utility.Result;

public final class Directory extends FileDescriptor {
    private static final int MAX_COUNT = 1 << 20;

    public static Result<Directory> creat(String pathname, BitSet<FilePermission> mode) {
        String normalized = FileSystem.normalize(pathname);

        int identifier = SystemCall.mkdir(normalized, mode.value());
        if (identifier == -1) {
            return Result.error(new Panic());
        }
        return Result.ok(new Directory(normalized, mode, identifier));
    }

    public Directory(String pathname, BitSet<FilePermission> mode, int identifier) {
        super(pathname, mode, identifier);
    }

    public Result<DirectoryEntry[]> list() {
        FileStatistic stat = SystemCall.fstat(identifier());
        if (stat == null) {
            return Result.error(new Panic());
        }

        long count = Math.min(stat.blockSize, MAX_COUNT);
        DirectoryEntry[] dirents = SystemCall.getdents(identifier(), count);
        if (dirents == null) {
            return Result.error(new Panic());
        }
        return Result.ok(dirents);
    }
}
