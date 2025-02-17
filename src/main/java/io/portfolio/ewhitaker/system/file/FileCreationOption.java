package io.portfolio.ewhitaker.system.file;

public final class FileCreationOption extends FileOpenOption {
    public static final FileCreationOption CREATE = new FileCreationOption(0100);

    private FileCreationOption(int value) {
        super(value);
    }
}
