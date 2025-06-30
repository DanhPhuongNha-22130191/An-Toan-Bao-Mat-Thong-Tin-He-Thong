package context;

public enum KeySize {
    SIZE_1024(1024),
    SIZE_2048(2048),
    SIZE_3072(3072),
    SIZE_4096(4096);

    private final int size;

    KeySize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
}
