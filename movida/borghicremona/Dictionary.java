package movida.borghicremona;

public interface Dictionary {
    public boolean search(Comparable key);

    public void insert(KeyValueElement item);

    public void delete(Comparable key);
}
