package io.portfolio.ewhitaker.system.file;

public enum FileCreationOption implements FileOpenOption {
    CREATE(0100),
    TRUNCATE(01000);

    private final int value;

    FileCreationOption(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
