import java.time.LocalDate;
import java.util.ArrayList;

public class Course {
    private String name = "EMPTY";
    private LocalDate date;
    private String room = "";

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
