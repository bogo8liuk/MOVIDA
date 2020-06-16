package movida.borghicremona;

public class IntegerKeyValueElement extends KeyValueElement {
    final private Integer key;

    public IntegerKeyValueElement(Integer key, Object data) {
        this.key = key;
        this.data = data;
    }

    public Integer getKey() {
        return this.key;
    }
}
