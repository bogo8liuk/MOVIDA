package movida.borghicremona;

public interface Dictionary {
    public boolean search(String key);

    public void insert(KeyValueElement item);

    public void delete(String key);
}
