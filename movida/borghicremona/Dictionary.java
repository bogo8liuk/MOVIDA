package movida.borghicremona;

public interface Dictionary {
	public Object search(Comparable key);

	public void insert(KeyValueElement item);

	public Object delete(Comparable key);
}
