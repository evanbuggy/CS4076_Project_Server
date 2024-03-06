import java.time.LocalDate;

public class Course {
    // These are the variables that will be set whenever a class is added to the schedule.
    private String name = "";
    private LocalDate date;
    private String room = "";

    public Course(String name, String room, LocalDate date) {
        this.name = name;
        this.date = date;
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }
}
