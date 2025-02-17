package io.portfolio.ewhitaker.system.file;

public enum FileAccessMode implements FileOpenOption {
    READ_ONLY(00),
    WRITE_ONLY(01),
    READ_WRITE(02);

    private final int value;

    FileAccessMode(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
