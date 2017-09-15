import java.util.List;

/**
 * ...
 * Mainly programmed by Martin S. Slavov.
 */
public class SorterPartOneAndTwo implements Runnable {
    private List<Integer> myIntegerList;

    public SorterPartOneAndTwo(List<Integer> integers) {
        myIntegerList = integers;
    }

    @Override
    public void run() {
        //This run also does an insertion sort, a duplicate of the one in MainPartOneAndTwo, as both main and multi threading need
        // them separate.
        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();
        //To prevent duplicate code, make use of the SortingAlgorithm class' insertionSort method.
        myIntegerList = sortingAlgorithms.insertionSort(myIntegerList);
    }

    public List<Integer> getMyIntegerList() {
        return myIntegerList;
    }
}