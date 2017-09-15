import java.util.ArrayList;
import java.util.List;

public class MainPartOneAndTwo {
    public static void main(String[] args) {
        new MainPartOneAndTwo().run();
    }

    /**
     * Method that can be used to select which of the three parts you want to run on MainPartOneAndTwo.
     */
    public void run() {
        //Change this number to run a different part of the assignment.
        int exercisePart = 3;

        //For choosing which exercise part to run. Used a switch before, but that decided to run all three regardless,
        // so we decided to use for-statements instead.
        if (exercisePart == 1) {
            runExercisePart1();
        } else if (exercisePart == 2) {
            runExercisePart2();
        } else if (exercisePart == 3) {
            runExercisePart3();
        }
    }

    /**
     * Method that does the exercise listed in Part 1 in the assignment folder.
     * In this assignment, a list of random numbers needs to be sorted on one thread.
     */
    public void runExercisePart1() {
        List<Integer> arrayListToSort = new ArrayList<>();
        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();

        //Change the number in this for loop clause to increase the size of the random ArrayList.
        for (int i = 0; i < 250000; i++) {
            arrayListToSort.add((int) ((Math.random() * 1000) + 1));
        }

        Long startTime = System.currentTimeMillis();
        //To prevent duplicate code, make use of the SortingAlgorithm class' insertionSort method.
        List<Integer> sortedArrayList = sortingAlgorithms.insertionSort(arrayListToSort);

        Long endTime = System.currentTimeMillis();

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by MAIN-THREAD insertion sort: " + totalTime + " ms.");
        //Outline to check if the list is actually sorted. Uncomment to check.
        // However, with large lists, you won't be able to see the timers anymore.
//        System.out.println("Sorted ArrayList: " + sortedArrayList.toString());
        //Tests performed (on an ASUS RoG Strix GL553VD laptop) with an array of random numbers between 1 and 1000.
        //Time taken for 10K = ~13 ms.
        //Time taken for 25K = ~1100 ms.
        //Time taken for 50K = ~3875 ms.
        //Time taken for 100K = ~13750 ms.
        //Time taken for 150K = ~33000 ms.
        //Time taken for 250K = ~160200 ms.
    }

    /**
     * Method that does the exercise listed in Part 1 in the assignment folder.
     * In this assignment, a list has to be split in two halves which are both sorted on separate threads and then merged back by the main thread.
     */
    public void runExercisePart2() {
        List<Integer> arrayListToSort = new ArrayList<>();
        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();
        //Change this number to increase the amount of random numbers in the unsorted ArrayList. Only enter numbers that
        // are round when divided by 2! (i.e. 4 (2+2), 20 (10+10), 100 (50+50), etc)
        int wantedArraySize = 250000;

        //Create an array of the given size with numbers that are between 1 and 1000.
        for (int i = 0; i < wantedArraySize; i++) {
            arrayListToSort.add((int) ((Math.random() * 1000) + 1));
        }

        //Have two sorters, each with their own half of the ArrayList. We do not have to care about number like 25
        // which can't be halved, as we won't be testing on those. Our minimum is 25000 after all (which can perfectly
        // be halved without rest numbers).
        SorterPartOneAndTwo s1 = new SorterPartOneAndTwo(arrayListToSort.subList(0, ((wantedArraySize / 2) - 1)));
        SorterPartOneAndTwo s2 = new SorterPartOneAndTwo(arrayListToSort.subList((wantedArraySize / 2), (wantedArraySize - 1)));

        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);

        //Now that the prep is done, start the timer.
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

        List<Integer> mergedSortedArrayList = sortingAlgorithms.mergeTwoArrayLists(firstSortedArrayList, secondSortedArrayList);

        //The program is done, so end the timer.
        Long endTime = System.currentTimeMillis();

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by DUAL-THREAD insertion sort: " + totalTime + " ms.");
        //Uncomment the following three lines to check if the sorting algorithm actually works.
        // However, with large lists, you won't be able to see the timers anymore.
//        System.out.println("First sorted ArrayList: " + firstSortedArrayList.toString());
//        System.out.println("Second sorted ArrayList: " + secondSortedArrayList.toString());
//        System.out.println("Merged sorted ArrayList: " + mergedSortedArrayList.toString());
        //Tests performed (on an ASUS RoG Strix GL553VD laptop) with an array of random numbers between 1 and 1000.
        //Time taken for 10K = ~140 ms.
        //Time taken for 25K = ~550 ms.
        //Time taken for 50K = ~1780 ms.
        //Time taken for 100K = ~6350 ms.
        //Time taken for 150K = ~13075 ms.
        //Time taken for 250K = ~41720 ms.
    }

    /**
     * Method that does the exercise listed in Part 1 in the assignment folder.
     * In this assignment, the list will be split into new halves and send to new threads every time the list is still
     * bigger than a given threshold. Once the leaves have been reached, it starts sorting and merging the lists back together.
     */
    public void runExercisePart3() {
        int wantedArraySize = 25000;

        SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();

        ArrayList<Integer> firstHalf = new ArrayList<>();
        ArrayList<Integer> secondHalf = new ArrayList<>();

        //Create two threads which will fill the lists, which later on will be sorted.
        Thread t1 = new SplittingPartThree(firstHalf, wantedArraySize / 2);
        Thread t2 = new SplittingPartThree(secondHalf, wantedArraySize / 2);

        //Start the timer now that the prep is done and the actual assignment is being worked on.
        Long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t3 = new SortingPartThree(firstHalf, wantedArraySize / 2);
        Thread t4 = new SortingPartThree(secondHalf, wantedArraySize / 2);

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Merge the two array lists.
        List<Integer> finalList = sortingAlgorithms.mergeTwoArrayLists(firstHalf, secondHalf);

        //Assignment is done, end the timer.
        Long endTime = System.currentTimeMillis();

        //As a quick safety check, do see if the list is actually sorted.
        for (int a = 1 ; a < finalList.size() ; a++){

            if (finalList.get(a-1) > finalList.get(a)){
                System.out.println("Final");
                System.out.println(finalList.get(a-1) + " " + finalList.get(a));
                break;
            }
        }

        Long totalTime = endTime - startTime;
        System.out.println("---[DONE]---");
        System.out.println("Total time taken by MULTI-THREAD insertion sort: " + totalTime + " ms.");
        //Uncomment to see result list. However, with large lists, you won't be able to see the timers anymore.
        System.out.println("Sorted ArrayList: " + finalList.toString());
        //Tests performed (on an ASUS RoG Strix GL553VD laptop) with an array of random numbers between 1 and 1000.
        //Time taken for 10K = ~... ms.
        //Time taken for 25K = ~... ms.
        //Time taken for 50K = ~... ms.
        //Time taken for 100K = ~... ms.
        //Time taken for 150K = ~... ms.
        //Time taken for 350K = ~... ms.
    }
}
