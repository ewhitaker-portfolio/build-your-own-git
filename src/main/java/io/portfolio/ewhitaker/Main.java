package io.portfolio.ewhitaker;

import io.portfolio.ewhitaker.system.file.FilePermission;
import io.portfolio.ewhitaker.system.file.FileDescriptor;
import io.portfolio.ewhitaker.system.SystemCall;
import io.portfolio.ewhitaker.system.file.File;
import io.portfolio.ewhitaker.system.file.FileStatistics;
import io.portfolio.ewhitaker.utility.BitSet;
import io.portfolio.ewhitaker.utility.Panic;

public interface Main {
    static void main(String[] args) {
        String pathname = "/home/treyvon/src/build-your-own-git/hello.txt";

        FileStatistics statbuf = new FileStatistics();
        if (SystemCall.stat(pathname, statbuf) == 0) {
            throw new Panic("the initial stat call passed");
        }

        FileDescriptor fd = File.creat(
                pathname,
                BitSet.of(
                        FilePermission.USER_READ,
                        FilePermission.USER_WRITE,
                        FilePermission.GROUP_READ,
                        FilePermission.GROUP_WRITE,
                        FilePermission.OTHER_READ
                )
        ).get();

        if (SystemCall.stat(pathname, statbuf) != 0) {
            throw new Panic("the final stat call failed");
        }
        System.out.printf("type %d\n", statbuf.getType());
    }
}
