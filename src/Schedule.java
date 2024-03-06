import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Schedule {

    private HashMap<Integer, Classes> list = new HashMap<Integer, Classes>();

    public Schedule() {
        for (int i = 9; i <= 18; i++) {
            // Initialising the HashMap. 9am and 6pm are the time slots for class.
            list.put(i, new Classes());
        }
    }

    public String add(String name, String room, LocalDate date, int time) {
        Course c = new Course(name, room, date);
        // Returns whether adding the class succeeded or failed.
        return list.get(time).addClass(c);
    }
}
