public interface Dictionary {
    public boolean search(Comparable key);

    public void insert(Comparable key, Object item);

    public void delete(Comparable key);
}
