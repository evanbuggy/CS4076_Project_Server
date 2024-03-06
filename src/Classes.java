import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Classes {
    private ArrayList<Course> list = new ArrayList<Course>();

    public String addClass(Course c) {
        LocalDate date = c.getDate();
        String name = c.getName();
        String room = c.getRoom();

        for (Course course : list) {
            if (date == course.getDate() && Objects.equals(name, course.getName()) && Objects.equals(room, course.getRoom())) {
                return "ADD_CLASS_FAIL";
            }
        }
        list.add(c);
        return "ADD_CLASS_SUCCESS";
    }
}
