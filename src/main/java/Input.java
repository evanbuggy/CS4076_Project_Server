import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Input {

    Schedule s = new Schedule();

    public synchronized String put(String in) {
        // The "temp" array should have a size of 5.
        if (Objects.equals(in, "STOP")) {
            return "TERMINATE";
        }
        String success = "";
        String[] temp;
        try {
            temp = in.split("_");
        }
        catch (NullPointerException e) {
            System.out.println("Input: Client has disconnected and/or the message received is null");
            return "TERMINATE";
        }
        System.out.print("Input: The parameters of the message are: ");
        for (int i = 0; i < temp.length; i++) {
            System.out.print(temp[i] + ", ");
        }
        System.out.println();
        try {
            switch(temp[0]) {
                // "METHOD_NAME_ROOM_YYYY-MM-DD_TIME"
                case "ADD":
                    System.out.println("Input: ADD has been called.");
                    return add(temp);
                // "METHOD_NAME_ROOM_YYYY-MM-DD_TIME"
                case "REMOVE":
                    System.out.println("Input: REMOVE has been called.");
                    return remove(temp);
                // "METHOD_YYYY-MM-DD"
                case "VIEW":
                    System.out.println("Input: VIEW has been called.");
                    return view(temp);
                // "METHOD_YYYY-MM-DD_NAME"
                case "VIEWCLASS":
                    System.out.println("Input: VIEWCLASS has been called.");
                    return viewClass(temp);
                // "METHOD"
                case "EARLYLECTURES":
                    System.out.println("Input: EARLYLECTURES has been called.");
                    return earlyLectures();
                default:
                    throw new IncorrectActionException("ERR: The input is an invalid response from the client!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }
    }

    private String add(String[] in) {
        String c = check(in);
        if (!Objects.equals(check(in), "OK")) {
            return c;
        }
        return s.add(in[1], in[2], LocalDate.parse(in[3]), Integer.parseInt(in[4]));
    }

    private String remove(String[] in) {
        String c = check(in);
        if (!Objects.equals(check(in), "OK")) {
            return c;
        }
        return s.remove(in[1], in[2], LocalDate.parse(in[3]), Integer.parseInt(in[4]));
    }

    private String earlyLectures() {
        System.out.println("--- earlyLectures() CALLED ---");
        HashMap<Integer, Classes> newMap = s.getMap();
        newMap.forEach((k, v) -> {
            if (k != 9) {
                System.out.println("The key is " + k);
                Classes earlySlots = s.getMap().get(9);
                earlySlots.printCourses();
                System.out.println("*** Fork/Join being called from Input class...");
                Classes unable_to_move = EarlyLectureSearch.Search(earlySlots, v);
                unable_to_move.printCourses();
                for (int i = 0; i < v.getSize(); i++) {
                    for (int j = 0; j < unable_to_move.getSize(); j++) {
                        if (!v.getClass(i).getDate().equals(unable_to_move.getClass(j).getDate()) ||
                            !Objects.equals(v.getClass(i).getRoom(), unable_to_move.getClass(j).getRoom())) {

                            System.out.println("Moving class to 9am slot...");
                            Classes temp = newMap.get(9);
                            temp.addClass(v.getClass(i));
                            newMap.put(9, temp);
                            v.removeClass(v.getClass(i));
                        }
                    }
                }
                System.out.println("Final result for " + k + ":");
                v.printCourses();
                System.out.println("*** Looping to next key in earlyLectures()...");
            }
        });
        return "Done!";
    }

    private String viewClass(String[] in) {
        LocalDate d = checkDate(in[1]);
        try {
            if (d == null) {
                throw new IncorrectActionException("ERR: Invalid date!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }
        StringBuilder result = new StringBuilder();
        ArrayList<String> temp = s.showClassesNamed(d, in[2]);
        for (String string : temp) {
            // ~ is used as the separator between entries
            result.append(string).append("~");
        }
        return result.toString();
    }

    private String view(String[] in) {
        LocalDate d = checkDate(in[1]);
        try {
            if (d == null) {
                throw new IncorrectActionException("ERR: Invalid date!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }
        StringBuilder result = new StringBuilder();
        ArrayList<String> temp = s.showClasses(d);
        for (String string : temp) {
            // ~ is used as the separator between entries
            result.append(string).append("~");
        }
        return result.toString();
    }

    private LocalDate checkDate(String d) {
        LocalDate temp;
        try {
            temp = LocalDate.parse(d);
        }
        catch (DateTimeParseException e) {
            return null;
        }
        return temp;
    }

    private boolean checkTime(String t) {
        int temp = 0;
        try {
            temp = Integer.parseInt(t);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return temp >= 9 && temp <= 17;
    }

    private String check(String[] in) {
        try {
            if (in.length != 5) {
                throw new IncorrectActionException("ERR: Not enough arguments!");
            }
            LocalDate d = checkDate(in[3]);
            if (d == null) {
                throw new IncorrectActionException("ERR: Invalid date!");
            }
            if (!checkTime(in[4])) {
                throw new IncorrectActionException("ERR: Invalid time slot!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }

        return "OK";
    }
}
