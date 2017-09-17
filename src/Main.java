import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Method that can be used to select which of the three parts you want to run on Main.
     * Mainly programmed by Yoran Kerbusch.
     */
    public void run() {
        //TODO: Change this number to run a different part of the assignment.
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
     * Mainly programmed by Yoran Kerbusch.
     */
    public void runExercisePart1() {
        //TODO: Change this number to influence how many times the program will run.
        double amountOfTimesToRun = 10.0;
        //TODO: Change this number to influence how big the size that the program will sort is.
        int desiredArrayListSize = 25000;
        double overallTime = 0.0;

        for (int i = 1; i < (amountOfTimesToRun + 1); i++) {
            List<Integer> arrayListToSort = new ArrayList<>();
            SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();

            //Change the number in this for loop clause to increase the size of the random ArrayList.
            for (int j = 0; j < desiredArrayListSize; j++) {
                arrayListToSort.add((int) ((Math.random() * 1000) + 1));
            }

            Long startTime = System.currentTimeMillis();
            //To prevent duplicate code, make use of the SortingAlgorithm class' insertionSort method.
            List<Integer> sortedArrayList = sortingAlgorithms.insertionSort(arrayListToSort);

            Long endTime = System.currentTimeMillis();

            double totalTime = (endTime - startTime) / 1000.0;
            System.out.println("~[DONE] - Try " + i + " - Total time taken by MAIN-THREAD insertion sort: " + totalTime + " s.");
            //Add this timer to the total count to get the average over the amount of tests.
            overallTime += totalTime;
            //Outline to check if the list is actually sorted. Uncomment to check.
            // However, with large lists, you won't be able to see the timers anymore.
//            System.out.println("Sorted ArrayList: " + sortedArrayList.toString());
        }
        //Display the average time taken.
        overallTime = overallTime / amountOfTimesToRun;
        System.out.println("Average time taken: " + overallTime + " s.");
    }

    /**
     * Method that does the exercise listed in Part 1 in the assignment folder.
     * In this assignment, a list has to be split in two halves which are both sorted on separate threads and then merged back by the main thread.
     * Mainly programmed by Yoran Kerbusch.
     */
    public void runExercisePart2() {
        //TODO: Change this number to influence how many times the program will run.
        double amountOfTimesToRun = 10.0;
        //TODO: Change this number to influence how big the size that the program will sort is. Please only fill in numbers than can be divided by 2.
        int desiredArrayListSize = 25000;
        double overallTime = 0.0;

        for (int i = 1; i < (amountOfTimesToRun + 1); i++) {
            List<Integer> arrayListToSort = new ArrayList<>();
            SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();
            //Change this number to increase the amount of random numbers in the unsorted ArrayList. Only enter numbers that
            // are round when divided by 2! (i.e. 4 (2+2), 20 (10+10), 100 (50+50), etc)

            //Create an array of the given size with numbers that are between 1 and 1000.
            for (int j = 0; j < desiredArrayListSize; j++) {
                arrayListToSort.add((int) ((Math.random() * 1000) + 1));
            }

            //Have two sorters, each with their own half of the ArrayList. We do not have to care about number like 25
            // which can't be halved, as we won't be testing on those. Our minimum is 25000 after all (which can perfectly
            // be halved without rest numbers).
            SorterPartOneAndTwo s1 = new SorterPartOneAndTwo(arrayListToSort.subList(0, ((desiredArrayListSize / 2) - 1)));
            SorterPartOneAndTwo s2 = new SorterPartOneAndTwo(arrayListToSort.subList((desiredArrayListSize / 2), (desiredArrayListSize - 1)));

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

            double totalTime = (endTime - startTime) / 1000.0;
            System.out.println("~[DONE] - Try " + i + " - Total time taken by DOUBLE-THREAD insertion sort: " + totalTime + " s.");
            //Add this timer to the total count to get the average over the amount of tests.
            overallTime += totalTime;
            //Uncomment the following three lines to check if the sorting algorithm actually works.
            // However, with large lists, you won't be able to see the timers anymore.
//            System.out.println("First sorted ArrayList: " + firstSortedArrayList.toString());
//            System.out.println("Second sorted ArrayList: " + secondSortedArrayList.toString());
//            System.out.println("Merged sorted ArrayList: " + mergedSortedArrayList.toString());
        }
        //Display the average time taken.
        overallTime = overallTime / amountOfTimesToRun;
        System.out.println("Average time taken: " + overallTime + " s.");
    }

    /**
     * Method that does the exercise listed in Part 1 in the assignment folder.
     * In this assignment, the list will be split into new halves and send to new threads every time the list is still
     * bigger than a given threshold. Once the leaves have been reached, it starts sorting and merging the lists back together.
     * Mainly programmed by Martin S. Slavov.
     */
    public void runExercisePart3() {
        //TODO: Change this number to influence how many times the program will run.
        double amountOfTimesToRun = 10.0;
        //TODO: Change this number to influence how big the size that the program will sort is.
        int desiredArrayListSize = 200000;
        //TODO: To change the threshold, go to the SortingPartThree.java class.
        //Value that stores all the times to ultimately calculate the average time taken by the amount of tests done at once.
        double overallTime = 0.0;

        for (int i = 1; i < (amountOfTimesToRun + 1); i++) {

            SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();
            ArrayList<Integer> firstHalf = new ArrayList<>();
            ArrayList<Integer> secondHalf = new ArrayList<>();

            //Create two threads which will put numbers into the two lists.
            Thread t1 = new SplittingPartThree(firstHalf, desiredArrayListSize / 2);
            Thread t2 = new SplittingPartThree(secondHalf, desiredArrayListSize / 2);

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

            //Create the first two threads which will start the splitting of the lists.
            Thread t3 = new SortingPartThree(firstHalf, desiredArrayListSize / 2);
            Thread t4 = new SortingPartThree(secondHalf, desiredArrayListSize / 2);

            t3.start();
            t4.start();

            try {
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Once the sorting and splitting is finished the two halves are merged.
            List<Integer> finalList = sortingAlgorithms.mergeTwoArrayLists(firstHalf, secondHalf);

            //Assignment is done, end the timer.
            Long endTime = System.currentTimeMillis();

            //As a quick safety check, do see if the list is actually sorted.
            boolean ok = true;
            for (int a = 1; a < finalList.size(); a++) {

                if (finalList.get(a - 1) > finalList.get(a)) {
                    System.out.println("Final");
                    System.out.println(finalList.get(a - 1) + " " + finalList.get(a));
                    ok = false;
                    break;
                }
            }

            double totalTime = (endTime - startTime) / 1000.0;
            System.out.println("~[DONE] - Try " + i + " - Total time taken by MULTI-SPLIT-THREAD insertion sort: " + totalTime + " s.");
            if (ok){
                System.out.println("ok");
            }
            //Add this timer to the total count to get the average over the amount of tests.
            overallTime += totalTime;
            //Uncomment to see result list. However, with large lists, you won't be able to see the timers anymore.
//            System.out.println("Sorted ArrayList: " + finalList.toString());
        }
        //Display the average time taken.
        overallTime = overallTime / amountOfTimesToRun;
        System.out.println("Average time taken: " + overallTime + " s.");
    }
}
