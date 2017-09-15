import java.util.ArrayList;
import java.util.List;

public class SortingPartThree extends Thread {

    private List<Integer> list;
    private int max;

    SortingPartThree(List<Integer> list, int max){
        this.list = list;
        this.max = max;
    }

    @Override
    public void run() {

//        list.size() >= max
//        list.size()%2 == 0

        //If the list is dividable by two, and is also above a certain thresh hold, split it into two and continue
        //repeating the process until the thresh hold is met or the number is no longer dividable.
        if (list.size() % 2 == 0) {
            if (list.size() >= max) {

                //The list that is passed is split into two halves.
                List<Integer> list1 = new ArrayList<>();
                List<Integer> list2 = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    if (i < list.size() / 2)
                        list1.add(list.get(i));
                    else
                        list2.add(list.get(i));
                }


                int threshold = 50000;

                //These two halves go through the same process(split if possible, otherwise sort them and return them).
                Thread t1 = new SortingPartThree(list1, threshold);
                Thread t2 = new SortingPartThree(list2, threshold);

                t1.start();
                t2.start();

                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //The two halves are merged into the list that was initially passed to this thread.
                try {
                    mergeIntoExistingList(list1, list2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Safety check to see if everything is OK.
//            for (int a = 1 ; a < list.size() ; a++){
//                if (list.get(a-1) > list.get(a)){
//                    System.out.println("FromThread " + max);
//                    System.out.println(list.get(a-1) + " " + list.get(a));
//                    break;
//                }
//            }

                //Some code that informs with what list sizes the threads work.
                System.out.println("From branch" + list.size());

            }
            else {

                int index = 1;
                while (index < list.size()) {
                    int element = list.get(index);
                    int newIndex = index;
                    while (newIndex > 0 && list.get(newIndex - 1) > element) {
                        int newNumber = list.get(newIndex - 1);
                        list.set(newIndex, newNumber);
                        newIndex = newIndex - 1;
                    }
                    if (list.get(newIndex) >= element) {
                        list.set(newIndex, element);
                        index = index + 1;
                    }
                }
                System.out.println(list.size() + "From leaf");
            }
        }
        else {

            int index = 1;
            while (index < list.size()) {
                int element = list.get(index);
                int newIndex = index;
                while (newIndex > 0 && list.get(newIndex - 1) > element) {
                    int newNumber = list.get(newIndex - 1);
                    list.set(newIndex, newNumber);
                    newIndex = newIndex - 1;
                }
                if (list.get(newIndex) >= element) {
                    list.set(newIndex, element);
                    index = index + 1;
                }
            }
            System.out.println(list.size() + "From leaf");
        }
    }


    private void mergeIntoExistingList(List<Integer> list1, List<Integer> list2) throws InterruptedException {
        int i = 0, j = 0, k = 0;

        while (i < list1.size() && j < list2.size()) {

            if (list1.get(i) < list2.get(j)) {
                list.set(k, list1.get(i));
                k++;
                i++;
            } else {
                list.set(k, list2.get(j));
                k++;
                j++;
            }

        }
        while (i < list1.size()) {
            list.set(k, list1.get(i));
            k++;
            i++;
        }
        while (j < list2.size()) {
            list.set(k, list2.get(j));
            k++;
            j++;
        }
    }
}
