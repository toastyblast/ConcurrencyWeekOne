import java.util.List;

public class SortingAlgorithms {
    public SortingAlgorithms() {
        //Empty constructor...
    }

    /**
     * Sort the list based on the insertion sort algorithm. Duplicate code with the Sorter class because for part 1 the
     * tests should be done on the main thread only.
     *
     * @param listToSort The list to be sorted
     * @return A sorted list, based on the comparable implementation of numbers.
     */
    public List<Integer> insertionSort(List<Integer> listToSort) {
        int index = 0;

        while (index < listToSort.size()) {
            int currentNumber = listToSort.get(index);
            int newIndex = index;

            while (newIndex > 0 && currentNumber < listToSort.get(newIndex - 1)) {
                listToSort.set(newIndex, listToSort.get(newIndex - 1));
                listToSort.set((newIndex - 1), currentNumber);

                newIndex--;
            }

            index++;
        }

        return listToSort;
    }
}
