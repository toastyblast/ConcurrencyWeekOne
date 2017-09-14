import java.util.ArrayList;
import java.util.List;

/**
 * A class that exists purely to have global methods for sorting and merging.
 */
public class SortingAlgorithms {
    public SortingAlgorithms() {
        //Empty constructor...
    }

    /**
     * Sort the list based on the insertion sort algorithm. Duplicate code with the SorterPartOneAndTwo class because for part 1 the
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

    /**
     * Method that merges the two given lists into one resulting list.
     *
     * @param firstSortedArrayList is the first list given to merge.
     * @param secondSortedArrayList is the second list given to merge with the first list.
     * @return a list that is the two given lists merged together.
     */
    public List<Integer> mergeTwoArrayLists(List<Integer> firstSortedArrayList, List<Integer> secondSortedArrayList) {
        List<Integer> mergedSortedArrayList = new ArrayList<>();
        int indexFirstList = 0;
        int indexSecondList = 0;

        //Merge the two sorted ArrayLists...
        //Continue this loop as long as both lists still have items inside them, until one is empty.
        while (indexFirstList < firstSortedArrayList.size() && indexSecondList < secondSortedArrayList.size()) {
            if (firstSortedArrayList.get(indexFirstList) < secondSortedArrayList.get(indexSecondList)) {
                //If the number at the firstIndex in list one is smaller than the number at the index in list two,
                // add the list one number and increase the index to the next number in list one.
                mergedSortedArrayList.add(firstSortedArrayList.get(indexFirstList));
                indexFirstList++;
            }
            else {
                //If the number at the secondIndex in list two is equal to or smaller than the number at the firstIndex in list one,
                // add the list two number and increase the secondIndex to the next number in list two.
                mergedSortedArrayList.add(secondSortedArrayList.get(indexSecondList));
                indexSecondList++;
            }
        }
        //Put any leftovers list 1 into the merged list, meaning that list 2 was empty before list 1.
        while (indexFirstList < firstSortedArrayList.size()) {
            mergedSortedArrayList.add(firstSortedArrayList.get(indexFirstList));
            indexFirstList++;
        }
        //Put any leftovers of list 2 into the merged list, meaning that list 1 was empty before list 2.
        while (indexSecondList < secondSortedArrayList.size()) {
            mergedSortedArrayList.add(secondSortedArrayList.get(indexSecondList));
            indexSecondList++;
        }

        return mergedSortedArrayList;
    }
}
