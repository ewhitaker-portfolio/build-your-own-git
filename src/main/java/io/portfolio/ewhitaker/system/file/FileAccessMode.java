package io.portfolio.ewhitaker.system.file;

public final class FileAccessMode extends FileOpenOption {
    public static final FileAccessMode READ_ONLY = new FileAccessMode(00);
    public static final FileAccessMode WRITE_ONLY = new FileAccessMode(01);
    public static final FileAccessMode READ_WRITE = new FileAccessMode(02);

    private FileAccessMode(int value) {
        super(value);
    }
}
