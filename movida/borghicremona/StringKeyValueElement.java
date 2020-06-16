package movida.borghicremona;

public class StringKeyValueElement extends KeyValueElement {
    final private String key;

    public StringKeyValueElement(String key, Object data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return this.key;
    }
}
