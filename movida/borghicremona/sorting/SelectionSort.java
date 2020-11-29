package movida.borghicremona.sorting;

public class Sort {
    public static void SelectionSort(Comparable[] vector){
        for (int i = 0; vector.length - 1 > i; ++i) {
            int minimum = i;

            for (int j = i + 1; vector.length > j; ++j){
                if (vector[minimum].compareTo(vector[j]) > 0)
                    minimum = j;
            }

            if (minimum != i) {
                Comparable temp = vector[minimum];
                vector[minimum] = vector[i];
                vector[i] = temp;
            }
        }
    }

    public static void QuickSort(Comparable[] vector){
    
    }

}
