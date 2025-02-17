package io.portfolio.ewhitaker;

import io.portfolio.ewhitaker.system.file.FileAccessMode;
import io.portfolio.ewhitaker.system.file.FileCreationOption;
import io.portfolio.ewhitaker.system.file.FilePermission;
import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.system.file.FileStatistic;
import io.portfolio.ewhitaker.system.file.FileStatistic.Type;
import io.portfolio.ewhitaker.system.file.FileSystem;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;

public interface Main {
    static void main(String[] args) {
        String pathname = "/home/treyvon/src/build-your-own-git/hello.txt";

        FileStatistic statbuf = new FileStatistic();
        if (SystemCall.stat(pathname, statbuf) == 0) {
            throw new Panic("hello.txt should not exist yet");
        }

        FileSystem.open(
                pathname,
                BitSet.of(
                        FileAccessMode.WRITE_ONLY,
                        FileCreationOption.CREATE,
                        FileCreationOption.TRUNCATE
                ),
                BitSet.of(
                        FilePermission.USER_READ,
                        FilePermission.USER_WRITE,
                        FilePermission.GROUP_READ,
                        FilePermission.GROUP_WRITE,
                        FilePermission.OTHER_READ
                )
        );

        if (SystemCall.stat(pathname, statbuf) != 0) {
            throw new Panic("hello.txt should exist");
        }

        if (statbuf.getType() != Type.REGULAR_FILE) {
            throw new Panic("hello.txt should be a file");
        }
    }
}
