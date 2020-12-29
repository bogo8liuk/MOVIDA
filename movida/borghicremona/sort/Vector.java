package movida.borghicremona.sort;

import java.util.Random;

/* Class that encapsulates an array of Comparable and it offers methods to perform sorting
   and shuffling on the array. */
public class Vector<T extends Comparable<T>> {
	private T[] vector;

	public Vector(T[] array) {
		try {
			if (null == array) throw new IllegalArgumentException("Array must be instantiated");
		} catch (IllegalArgumentException exception) {
			System.err.println(exception.getMessage());
			System.exit(-1);
		}

		this.vector = array;
	}

	/**
	 * It swaps two elements in an array.
	 *
	 * @param array Array where the swapping has to be performed.
	 * @param index1 Index of the first element to swap.
	 * @param index2 Index of the second element to swap.
	 *
	 * @attention The validity of the two indexes is an unchecked runtime error.
	 */
	private void swap(T[] array, Integer index1, Integer index2) {
		T tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
	}

	/**
	 * It performs a pseudo-casual shuffling.
	 */
	public void shuffle() {
		Integer length = this.vector.length;

		for (int i = 0; length > i; ++i) {
			Random rand = new Random();
			int j = rand.nextInt(length);
			swap(this.vector, i, j);
		}
	}

	/**
	 * It performs the selection sort algorithm.
	 */
	public void selectionSort() {
		for (int i = 0; this.vector.length - 1 > i; ++i) {
			int minIndex = i;

			for (int j = i + 1; this.vector.length > j; ++j) {
				if (this.vector[minIndex].compareTo(this.vector[j]) > 0)
					minIndex = j;
			}

			swap(this.vector, minIndex, i);
		}
	}

	/**
	 * It increments an index within an array until the indexed element is less than a
	 * specific element in an array.
	 *
	 * @param array The array of reference.
	 * @param toShift The index to increment.
	 * @param rightExtreme The index that toShift has not to come over.
	 * @param pivot The element to compare every time with the toShift-th element.
	 *
	 * @return The new value of the incremented index.
	 *
	 * @attention The validity of the indexes in an unchecked runtime error.
	 * @attention The validity and the existence of the pivot in the array is an
	 * unchecked runtime error.
	 */
	private Integer rightShift(T[] array, Integer toShift, Integer rightExtreme, T pivot) {
		do {
			++toShift;
		} while (toShift <= rightExtreme && array[toShift].compareTo(pivot) <= 0);

		return toShift;
	}

	/**
	 * It decrements an index within an array until the indexed element is greater than a
	 * specific element in an array.
	 *
	 * @param array The array of reference.
	 * @param toShift The index to decrement.
	 * @param pivot The element to compare every time with the toShift-th element.
	 *
	 * @return The new value of the decremented index.
	 *
	 * @attention The validity of the index in an unchecked runtime error.
	 * @attention The validity and the existence of the pivot in the array is an
	 * unchecked runtime error.
	 */
	private Integer leftShift(T[] array, Integer toShift, T pivot) {
		do {
			--toShift;
		} while (array[toShift].compareTo(pivot) > 0);

		return toShift;
	}

	/**
	 * It moves the elements within the two indexes of an array in order to have all the
	 * elements less than a specific element to its "left" and the greater elements to its
	 * "right".
	 *
	 * @param array The array where the moving has to be performed.
	 * @param leftExtreme The first index.
	 * @param rightExtreme The second index.
	 *
	 * @return The index of the element around which the moving has been performed.
	 *
	 * @attention The validity of the two indexes is an unchecked runtime error.
	 */
	private Integer partition(T[] array, Integer leftExtreme, Integer rightExtreme) {
		Integer inf = leftExtreme;
		Integer sup = rightExtreme + 1;

		while (inf < sup) {
			// Pivot chosed at the first position.
			inf = rightShift(array, inf, rightExtreme, array[leftExtreme]);
			sup = leftShift(array, sup, array[leftExtreme]);
			if (inf < sup)
				swap(array, inf, sup);
		}

		swap(array, sup, leftExtreme);
		return sup;
	}

	private void __quickSort(T[] array, Integer leftExtreme, Integer rightExtreme) {
		if (leftExtreme >= rightExtreme) return;
		Integer edge = partition(array, leftExtreme, rightExtreme);
		__quickSort(array, leftExtreme, edge - 1);
		__quickSort(array, edge + 1, rightExtreme);
	}

	/**
	 * It performs the quick sort algorithm.
	 */
	public void quickSort() {
		__quickSort(this.vector, 0, this.vector.length - 1);
	}

	public T[] getArray() {
		return this.vector;
	}
}
