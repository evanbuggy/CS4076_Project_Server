import java.util.HashMap;

public class Schedule {

    private HashMap<Integer, Course> list = new HashMap<Integer, Course>();

    public Schedule() {
        for (int i = 9; i <= 18; i++) {
            list.put(i, new Course());
        }
    }
}
