package com.company;

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
        int index = 0;

        while (index < myIntegerList.size()) {
            int currentNumber = myIntegerList.get(index);
            int newIndex = index;

            while (newIndex > 0 && currentNumber < myIntegerList.get(newIndex - 1)) {
                myIntegerList.set(newIndex, myIntegerList.get(newIndex - 1));
                myIntegerList.set((newIndex - 1), currentNumber);

                newIndex--;
            }

            index++;
        }
    }

    public List<Integer> getMyIntegerList() {
        return myIntegerList;
    }
}
