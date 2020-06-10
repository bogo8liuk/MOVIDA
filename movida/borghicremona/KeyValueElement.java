package movida.borghicremona;

public class KeyValueElement {
    final private String key;
    private Object data;

    public KeyValueElement(String key, Object data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return this.key;
    }
}
