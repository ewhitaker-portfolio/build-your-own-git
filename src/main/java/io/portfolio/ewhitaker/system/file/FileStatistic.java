package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.Panic;

public final class FileStatistic {
    public enum Type {
        DIRECTORY(0x4000),
        REGULAR_FILE(0x8000);

        public final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public Type type;
    public long blockSize;

    public FileStatistic() {
        super();
    }

    public Type getType() {
        return this.type;
    }

    // TODO: refactor to map after those are added to utils
    public void setType(int typeValue) {
        Type found = null;
        for (Type type : Type.values()) {
            if (type.value() == typeValue) {
                found = type;
                break;
            }
        }
        if (found == null) {
            throw new Panic();
        }
        this.type = found;
    }

    public long getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(long blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public String toString() {
        return "FileStatistic{" +
                "type=" + this.type +
                ",blockSize=" + this.blockSize +
                "}";
    }
}
