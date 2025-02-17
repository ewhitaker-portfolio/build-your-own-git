package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.Flag;

public sealed abstract class FileOpenOption implements Flag
        permits FileAccessMode, FileCreationOption, FileStatusOption {
    private final int value;

    public FileOpenOption(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
