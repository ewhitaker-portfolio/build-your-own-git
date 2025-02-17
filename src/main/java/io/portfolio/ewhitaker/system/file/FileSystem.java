package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;
import io.portfolio.ewhitaker.utility.Result;

public final class FileSystem {
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String USER_DIRECTORY = System.getProperty("user.dir");

    public static String normalize(String pathname) {
        return pathname;
    }

    public static String resolve(String directory, String pathname) {
        return directory + pathname;
    }

    public static Result<FileDescriptor> open(
            String pathname, BitSet<FileOpenOption> flags, BitSet<FilePermission> mode) {
        String normalized = FileSystem.normalize(pathname);

        int fd;
        if (flags.contains(FileCreationOption.CREATE)) {
            fd = SystemCall.open(normalized, (int) flags.value(), mode.value());
        } else {
            fd = SystemCall.open(normalized, (int) flags.value());
        }

        if (fd < 0) {
            return Result.error(new Panic());
        }

        FileStatistic statbuf = new FileStatistic();
        if (SystemCall.fstat(fd, statbuf) != 0) {
            return Result.error(new Panic());
        }

        return switch (statbuf.getType()) {
            case DIRECTORY -> Result.ok(new Directory(normalized, mode, fd));
            case REGULAR_FILE -> Result.ok(new File(normalized, mode, fd));
        };
    }

    public static Result<FileStatistic> stat(String pathname) {
        String normalized = FileSystem.normalize(pathname);

        FileStatistic stat = new FileStatistic();
        int result = SystemCall.stat(normalized, stat);
        if (result != 0) {
            return Result.error(new Panic());
        }
        return Result.ok(stat);
    }

    private FileSystem() {
        super();
    }
}
