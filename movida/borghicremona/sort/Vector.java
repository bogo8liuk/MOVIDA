package movida.borghicremona.sort;

public class Vector<T extends Comparable<T>> {
	private T[] vector;

	public Vector(T[] array) {
		this.vector = array;
	}

	private void swap(T[] array, Integer index1, Integer index2) {
		T tmp = array[index1];
		array[index1] = array[index2];
		array[index2] = tmp;
	}

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

	private Integer rightShift(T[] array, Integer toShift, Integer rightExtreme, T pivot) {
		do {
			++toShift;
		} while (toShift <= rightExtreme && array[toShift].compareTo(pivot) <= 0);

		return toShift;
	}

	private Integer leftShift(T[] array, Integer toShift, T pivot) {
		do {
			--toShift;
		} while (array[toShift].compareTo(pivot) > 0);

		return toShift;
	}

	private Integer partition(T[] array, Integer leftExtreme, Integer rightExtreme) {
		Integer inf = leftExtreme;
		Integer sup = rightExtreme + 1;

		while (inf < sup) {
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

	public void quickSort() {
		__quickSort(this.vector, 0, this.vector.length - 1);
	}

	public T[] getArray() {
		return this.vector;
	}
}
