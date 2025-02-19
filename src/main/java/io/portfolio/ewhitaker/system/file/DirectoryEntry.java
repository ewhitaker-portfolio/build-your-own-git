package io.portfolio.ewhitaker.system.file;

public final class DirectoryEntry {
    public String name;

    public DirectoryEntry() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DirectoryEntry{" +
                "name='" + this.name + "'" +
                "}";
    }
}
