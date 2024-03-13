import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

public class Input {

    Schedule s = new Schedule();

    public String put(String in) {
        // Therefore the "temp" array should have a size of 5.
        String success = "";
        String[] temp = in.split("_");
        try {
            switch(temp[1]) {
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
                // "METHOD_NAME_YYYY-MM-DD"
                case "VIEWCLASS":
                    System.out.println("Input: VIEWCLASS has been called.");
                    return viewClass(temp);
                default:
                    throw new IncorrectActionException("The input is an invalid response from the client!");
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

    private String view(String[] in) {
        return null;
    }

    private String viewClass(String[] in) {
        LocalDate d = checkDate(in[3]);
        try {
            if (d == null) {
                throw new IncorrectActionException("Invalid date!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }
        String result = "";
        ArrayList<String> temp = s.showClasses(d);
        for (int i = 0; i < temp.size(); i++) {
            result += temp.get(i) + "\n";
        }
        return result;
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
                throw new IncorrectActionException("Not enough arguments!");
            }
            LocalDate d = checkDate(in[3]);
            if (d == null) {
                throw new IncorrectActionException("Invalid date!");
            }
            if (!checkTime(in[4])) {
                throw new IncorrectActionException("Invalid time slot!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }

        return "OK";
    }
}
