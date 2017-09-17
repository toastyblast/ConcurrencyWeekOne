import java.util.ArrayList;
import java.util.List;

/**
 * ...
 * Mainly programmed by Martin S. Slavov.
 */
public class SortingPartThree extends Thread {
    private List<Integer> list;
    private int max;

    SortingPartThree(List<Integer> list, int max) {
        this.list = list;
        this.max = max;
    }

    @Override
    public void run() {
        if (list.size() >= max) {
            //Two new lists are created and they get filled, with the numbers form the list that was passed to the
            //thread.
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() / 2)
                    list1.add(list.get(i));
                else
                    list2.add(list.get(i));
            }
            //Change values here to change the threshold
            Thread t1 = new SortingPartThree(list1, 10000);
            Thread t2 = new SortingPartThree(list2, 10000);

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
            //Merge the lists that were sorted by the threads.
            try {
                mergeIntoExistingList(list1, list2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Safety checks to see if the lists are sorted.
//            for (int a = 1 ; a < list.size() ; a++){
//                if (list.get(a-1) > list.get(a)){
//                    System.out.println("FromThread " + max);
//                    System.out.println(list.get(a-1) + " " + list.get(a));
//                    break;
//                }
//            }

//                System.out.println("From branch" + list.size());
        } else {
            //Insertion sort.
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

//                System.out.println(list.size() + "From leaf");
        }
    }

    /**
     * Merging algorithm, that merges the two lists that get passed to it.
     *
     * @param list1 List received from the first thread.
     * @param list2 List received from the second thread.
     * @throws InterruptedException
     */
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
