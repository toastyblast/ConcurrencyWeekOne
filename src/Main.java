import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        int exercisePart = 2;

        //For choosing which exercise part to run. Used a switch before, but that decided to run all three regardless,
        // so switched to for-statements instead
        if (exercisePart == 1) {
            runExercisePart1();
        } else if (exercisePart == 2) {
            runExercisePart2();
        } else if (exercisePart == 3) {
            runExercisePart3();
        }
    }

    public void runExercisePart1() {
        List<Integer> arrayListToSort = new ArrayList<>();
        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();

        for (int i = 0; i < 25000; i++) {
            arrayListToSort.add((int) ((Math.random() * 1000) + 1));
        }

        Long startTime = System.currentTimeMillis();

        List<Integer> sortedArrayList = sortingAlgorithms.insertionSort(arrayListToSort);

        Long endTime = System.currentTimeMillis();

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by MAIN-THREAD insertion sort: " + totalTime + " ms.");
        //Outline to check if the list is actually sorted. Uncomment to check.
//        System.out.println("Sorted ArrayList: " + sortedArrayList.toString());
        //Tests performed with an array of random numbers between 1 and 1000.
        //Time taken for 25K = ~1100 ms.
        //Time taken for 50K = ~3875 ms.
        //Time taken for 100K = ~13750 ms.
        //Time taken for 200K = ~73000 ms.
        //Time taken for 400K = TAKES TOO LONG TO WARRANT TESTING.
        //Time taken for 800K = TAKES TOO LONG TO WARRANT TESTING.
    }

    public void runExercisePart2() {
        List<Integer> arrayListToSort = new ArrayList<>();
        //Change this number to increase the amount of random numbers in the unsorted ArrayList. Only enter numbers that
        // are round when divided by 2! (i.e. 4 (2+2), 20 (10+10), 100 (50+50), etc)
        int wantedArraySize = 25000;

        for (int i = 0; i < wantedArraySize; i++) {
            arrayListToSort.add((int) ((Math.random() * 1000) + 1));
        }

        //Have two sorters, each with their own half of the ArrayList. We do not have to care about number like 25
        // which can't be halved, as we won't be testing on those. Our minimum is 25000 after all (which can perfectly
        // be halved without rest numbers).
        Sorter s1 = new Sorter(arrayListToSort.subList(0, ((wantedArraySize / 2) - 1)));
        Sorter s2 = new Sorter(arrayListToSort.subList((wantedArraySize / 2), (wantedArraySize - 1)));

        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);

        Long startTime = System.currentTimeMillis();

        //Start both threads, so that they sort their ArrayLists...
        t1.start();
        t2.start();

        try {
            //Wait for both threads to finish sorting...
            t1.join();
            t2.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        //Get our two sorted lists, so we can sort them.
        List<Integer> firstSortedArrayList = s1.getMyIntegerList();
        List<Integer> secondSortedArrayList = s2.getMyIntegerList();

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

        Long endTime = System.currentTimeMillis();

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by DUAL-THREAD insertion sort: " + totalTime + " ms.");
        //Uncomment the following three lines to check if the sorting algorithm actually works.
//        System.out.println("First sorted ArrayList: " + firstSortedArrayList.toString());
//        System.out.println("Second sorted ArrayList: " + secondSortedArrayList.toString());
//        System.out.println("Merged sorted ArrayList: " + mergedSortedArrayList.toString());
        //Tests performed with an array of random numbers between 1 and 1000.
        //Time taken for 25K = ~550 ms.
        //Time taken for 50K = ~1780 ms.
        //Time taken for 100K = ~6350 ms.
        //Time taken for 200K = ~25000 ms.
        //Time taken for 400K = TAKES TOO LONG TO WARRANT TESTING.
        //Time taken for 800K = TAKES TOO LONG TO WARRANT TESTING.
    }

    public void runExercisePart3() {
        List<Integer> arrayListToSort = new ArrayList<>();
        int wantedArraySize = 25000;
//        int newThreadThreshold = 10000;

        for (int i = 0; i < wantedArraySize; i++) {
            arrayListToSort.add((int) ((Math.random() * 1000) + 1));
        }

        //TODO: Do any prep that shouldn't be tracked here.

        Long startTime = System.currentTimeMillis();

        //TODO: PLACE PART 3 HERE

        List<Integer> sortedArrayList = new ArrayList<>();

        Long endTime = System.currentTimeMillis();

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by MULTI-THREAD insertion sort: " + totalTime + " ms.");
        System.out.println("Sorted ArrayList: " + sortedArrayList.toString());
        //Tests performed with an array of random numbers between 1 and 1000.
        //Time taken for 25K = ~... ms.
        //Time taken for 50K = ~... ms.
        //Time taken for 100K = ~... ms.
        //Time taken for 200K = ~... ms.
        //Time taken for 400K = ~... ms.
        //Time taken for 800K = ~... ms.
    }
}
