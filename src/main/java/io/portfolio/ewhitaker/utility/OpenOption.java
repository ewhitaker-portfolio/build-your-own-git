package io.portfolio.ewhitaker.utility;

import io.portfolio.ewhitaker.system.file.FileAccessMode;
import io.portfolio.ewhitaker.system.file.FileCreationOption;
import io.portfolio.ewhitaker.system.file.FileOpenOption;

public final class OpenOption {
    private final BitSet<FileOpenOption> bitset;

    private OpenOption() {
        this.bitset = new BitSet<>(0);
    }

    public static OpenOption builder() {
        return new OpenOption();
    }

    public OpenOption read() {
        if (this.bitset.contains(FileAccessMode.READ_WRITE)) {
            return this;
        }

        if (this.bitset.contains(FileAccessMode.WRITE_ONLY)) {
            this.bitset.unset(FileAccessMode.WRITE_ONLY);
            this.bitset.set(FileAccessMode.READ_WRITE);
        } else {
            this.bitset.set(FileAccessMode.READ_ONLY);
        }

        return this;
    }

    public OpenOption write() {
        if (this.bitset.contains(FileAccessMode.READ_WRITE)) {
            return this;
        }

        if (this.bitset.contains(FileAccessMode.READ_ONLY)) {
            this.bitset.unset(FileAccessMode.READ_ONLY);
            this.bitset.set(FileAccessMode.READ_WRITE);
        } else {
            this.bitset.set(FileAccessMode.WRITE_ONLY);
        }

        return this;
    }

    public OpenOption create() {
        this.bitset.set(FileCreationOption.CREATE);
        return this;
    }

    public BitSet<FileOpenOption> build() {
        return this.bitset;
    }
}
