import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Schedule {

    // This HashMap will contain the classes for the time slots between 9am and 6pm.
    private final HashMap<Integer, Classes> list = new HashMap<Integer, Classes>();

    public Schedule() {
        for (int i = 9; i <= 17; i++) {
            // Initialising the HashMap. 9am and 6pm are the time slots for class.
            list.put(i, new Classes());
        }
    }

    public String add(String name, String room, LocalDate date, int time) {
        Course c = new Course(name, room, date);
        // Returns whether adding the class succeeded or failed.
        return list.get(time).addClass(c);
    }

    public String remove(String name, String room, LocalDate date, int time) {
        Course c = new Course(name, room, date);
        // Returns whether removing the class succeeded or failed.
        return list.get(time).removeClass(c);
    }

    public ArrayList<String> showClasses(LocalDate date) {
        ArrayList<String> classes = new ArrayList<String>();
        list.forEach((k, v) -> {
            ArrayList<Course> temp = v.getClasses();
            for (Course c : temp) {
                if (date == c.getDate()) {
                    classes.add(k + "_" + c.getName() + "_" + c.getRoom());
                }
            }
        });
        return classes;
    }
}
