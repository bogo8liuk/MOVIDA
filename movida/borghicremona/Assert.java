package movida.borghicremona;
   
import movida.borghicremona.KeyValueElement;
import java.lang.RuntimeException;
import java.util.*;

public class Assert {
	
	/**
	 * If the item has null value, it throws an IllegalArgumentException.
	 *
	 * @param item Value to check.
	 */
	public static void notNullData(KeyValueElement item) throws IllegalArgumentException {
		if (null == item) throw new IllegalArgumentException("Invalid data");
	}
 
	/**
	 * If the key has null value, it throws an IllegalArgumentException.
	 *
	 * @param key Value to check
	 */
	public static void notNullKey(Comparable key) throws IllegalArgumentException {
		if (null == key) throw new IllegalArgumentException("Invalid key");
	}
}
