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
        String s = list.get(time).addClass(c);
        return switch (s) {
            case "ADD_CLASS_SUCCESS" -> "Successfully added class!";
            case "ADD_CLASS_FAIL" -> "There is already a class in that room with the same date and time!";
            default -> s;
        };
    }

    public String remove(String name, String room, LocalDate date, int time) {
        Course c = new Course(name, room, date);
        String s = list.get(time).removeClass(c);
        return switch (s) {
            case "REMOVE_CLASS_SUCCESS" -> "Successfully removed class!";
            case "REMOVE_CLASS_FAIL" -> "That class does not exist!";
            default -> s;
        };
    }

    public ArrayList<String> showClasses(LocalDate date) {
        ArrayList<String> classes = new ArrayList<String>();
        list.forEach((k, v) -> {
            ArrayList<Course> temp = v.getClasses();
            for (Course c : temp) {
                if (date.equals(c.getDate())) {
                    classes.add(k + "_" + c.getName() + "_" + c.getRoom());
                }
            }
        });
        return classes;
    }

    public ArrayList<String> showClassesNamed(LocalDate date, String name) {
        ArrayList<String> classes = new ArrayList<String>();
        list.forEach((k, v) -> {
            ArrayList<Course> temp = v.getClasses();
            for (Course c : temp) {
                if (date.equals(c.getDate()) && Objects.equals(name, c.getName())) {
                    classes.add(k + "_" + c.getName() + "_" + c.getRoom());
                }
            }
        });
        return classes;
    }
}
