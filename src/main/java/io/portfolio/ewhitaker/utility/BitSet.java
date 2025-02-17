package io.portfolio.ewhitaker.utility;

public final class BitSet<T extends Flag> {
    private long value;

    @SafeVarargs
    public static <T extends Flag> BitSet<T> of(T... flags) {
        int value = 0;
        for (Flag flag : flags) {
            value |= flag.value();
        }
        return new BitSet<>(value);
    }

    public BitSet(long value) {
        this.value = value;
    }

    public void set(T flag) {
        this.value |= flag.value();
    }

    public void unset(T flag) {
        this.value = (this.value & (~flag.value()));
    }

    public void flip(T flag) {
        this.value ^= flag.value();
    }

    public boolean contains(T flag) {
        return (this.value & flag.value()) != 0;
    }

    public long value() {
        return this.value;
    }
}
