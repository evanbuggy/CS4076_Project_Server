import java.util.HashMap;

public class Schedule {

    private HashMap<Integer, Course> list = new HashMap<Integer, Course>();

    public Schedule() {
        for (int i = 9; i <= 18; i++) {
            // Initialising the HashMap. 9am and 6pm are the time slots for class.
            list.put(i, new Course());
        }
    }
}
