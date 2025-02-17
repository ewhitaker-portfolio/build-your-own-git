package io.portfolio.ewhitaker.system.file;

import io.portfolio.ewhitaker.utility.Panic;

public final class FileStatistics {
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

    public FileStatistics() {
        super();
    }

    public Type getType() {
        return this.type;
    }

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
}
