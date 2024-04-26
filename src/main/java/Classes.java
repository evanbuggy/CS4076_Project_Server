import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Classes {
    // This is the list that contains all the classes that will be on.
    // When instantiated by Schedule, that means that there will be 8 of these
    // in total, one for each time slot (9am - 5pm).
    private ArrayList<Course> list = new ArrayList<Course>();

    public String addClass(Course c) {
        LocalDate date = c.getDate();
        String room = c.getRoom();

        for (Course course : list) {
            if (date.equals(course.getDate()) && Objects.equals(room, course.getRoom())) {
                return "ADD_CLASS_FAIL";
            }
        }
        list.add(c);
        return "ADD_CLASS_SUCCESS";
    }

    public String removeClass(Course c) {
        LocalDate date = c.getDate();
        String room = c.getRoom();
        String name = c.getName();
        // This time we create a variable for the name of the course.
        // This is because we add a 2nd condition where if the name the
        // client typed in is incorrect, the class cannot be removed.

        for (Course course : list) {
            if (date.equals(course.getDate()) && Objects.equals(room, course.getRoom())) {
                if (Objects.equals(name, course.getName())) {
                    list.remove(course);
                    return "REMOVE_CLASS_SUCCESS";
                }
                else {
                    return "REMOVE_CLASS_NAME_INVALID";
                }
            }
        }
        return "REMOVE_CLASS_FAIL";
    }

    public ArrayList<Course> getClasses() {
        return list;
    }

    public Course getClass(int i) {
        return list.get(i);
    }

    public int getSize() {
        return list.size();
    }

    public void addFromOtherClass(ArrayList<Course> c) {
        for (Course course : c) {
            addClass(course);
        }
    }

    public void printCourses() {
        System.out.println("This time interval has the following classes:");
        for (Course c : list) {
            System.out.println(c.getName() + ", " + c.getRoom() + ", " + c.getDate());
        }
        System.out.println();
    }
}
