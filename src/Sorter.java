import java.util.List;

public class Sorter implements Runnable {
    private List<Integer> myIntegerList;

    public Sorter(List<Integer> integers) {
        myIntegerList = integers;
    }

    @Override
    public void run() {
        //This run also does an insertion sort, a duplicate of the one in Main, as both main and multi threading need
        // them separate.
        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();

        myIntegerList = sortingAlgorithms.insertionSort(myIntegerList);
    }

    public List<Integer> getMyIntegerList() {
        return myIntegerList;
    }
}