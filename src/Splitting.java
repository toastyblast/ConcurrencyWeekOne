import java.util.ArrayList;
import java.util.List;

public class Splitting extends Thread {
    private List<Integer> list;
    private int max;

    Splitting(List<Integer> list, int max){
        this.list = list;
        this.max = max;
    }

    public void run(){
        for (int i = 0 ; i < max ; i++){
            int randomNumber = (int) ((Math.random() * max) + 1);
            list.add(randomNumber);
        }
    }
}
