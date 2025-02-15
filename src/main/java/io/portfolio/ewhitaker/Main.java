package io.portfolio.ewhitaker;

import io.portfolio.ewhitaker.system.FileDescriptor;
import io.portfolio.ewhitaker.system.FileDescriptor.File;

public interface Main {
    static void main(String[] args) {
        FileDescriptor fd = new File("/home/treyvon/src/build-your-own-git/hello.txt", 0664);
    }
}
