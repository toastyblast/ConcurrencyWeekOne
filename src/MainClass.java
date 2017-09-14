import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        new MainClass().run();
    }

    private void run() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        ArrayList<Integer> firstHalf = new ArrayList<>();
        ArrayList<Integer> secondHalf = new ArrayList<>();
        int amountOfNumbers = 2000;

        Thread t1 = new Splitting(firstHalf, amountOfNumbers / 2);
        Thread t2 = new Splitting(secondHalf, amountOfNumbers / 2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Thread t3 = new Sorting(firstHalf, amountOfNumbers/2);
        Thread t4 = new Sorting(secondHalf, amountOfNumbers/2);

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println(merge(firstHalf, secondHalf).size());
        List<Integer> finalList = merge(firstHalf,secondHalf);
        double endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime/1000);

        for (int a = 1 ; a < firstHalf.size() ; a++){
            if (firstHalf.get(a-1) > firstHalf.get(a)){
                System.out.println("First");
                System.out.println(firstHalf.get(a-1) + " " + firstHalf.get(a));
                break;
            }
        }

        for (int a = 1 ; a < secondHalf.size() ; a++){
            if (secondHalf.get(a-1) > secondHalf.get(a)){
                System.out.println("Second");
                System.out.println(secondHalf.get(a-1) + " " + secondHalf.get(a));
                break;
            }
        }

        for (int a = 1 ; a < finalList.size() ; a++){
            if (finalList.get(a-1) > finalList.get(a)){
                System.out.println("Final");
                System.out.println(finalList.get(a-1) + " " + finalList.get(a));
                break;
            }
        }

    }

    private List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> finalList = new ArrayList<>();
        int i = 0, j = 0, k = 0;
        while (i < list1.size() && j < list2.size()) {

            if (list1.get(i) < list2.get(j)) {
                finalList.add(list1.get(i));
                i++;
            } else {
                finalList.add(list2.get(j));
                j++;
            }

        }
        while (i < list1.size()) {
            finalList.add(list1.get(i));
            i++;
        }
        while (j < list2.size()) {
            finalList.add(list2.get(j));
            j++;
        }

        return finalList;

    }
}
