import java.util.List;

/**
 * ...
 * Mainly programmed by Martin S. Slavov.
 */
public class SplittingPartThree extends Thread {
    private List<Integer> list;
    private int max;

    SplittingPartThree(List<Integer> list, int max) {
        this.list = list;
        this.max = max;
    }

    public void run() {
        for (int i = 0; i < max; i++) {
            int randomNumber = (int) ((Math.random() * max) + 1);
            list.add(randomNumber);
        }
    }
}
