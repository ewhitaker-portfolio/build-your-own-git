package io.portfolio.ewhitaker;

import io.portfolio.ewhitaker.system.file.Directory;
import io.portfolio.ewhitaker.system.file.DirectoryEntry;
import io.portfolio.ewhitaker.system.file.FileAccessMode;
import io.portfolio.ewhitaker.system.file.FileCreationOption;
import io.portfolio.ewhitaker.system.file.FileSystem;
import io.portfolio.ewhitaker.utility.BitSet;

public interface Main {
    static void main(String[] args) {
        String pathname = "/home/treyvon/src/build-your-own-git";

        Directory dir = (Directory) FileSystem.open(
                pathname,
                BitSet.of(
                        FileAccessMode.READ_ONLY,
                        FileCreationOption.DIRECTORY
                ),
                null
        ).get();

        for (DirectoryEntry dirent : dir.list().get()) {
            System.out.printf("%s\n", dirent);
        }
    }
}
