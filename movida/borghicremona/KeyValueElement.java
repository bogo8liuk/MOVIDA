package movida.borghicremona;

public class KeyValueElement {
    final protected Comparable key;
    protected Object data;

    public KeyValueElement() {
        this.key = null;
        this.data = null;
    }

    public KeyValueElement(Comparable key, Object data) {
        this.key = key;
        this.data = data;
    }

    public Comparable getKey() {
        return this.key;
    }
}
