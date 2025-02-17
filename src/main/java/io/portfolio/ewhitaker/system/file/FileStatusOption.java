package io.portfolio.ewhitaker.system.file;

public enum FileStatusOption implements FileOpenOption {
    ;
    private final int value;

    FileStatusOption(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
