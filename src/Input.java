public class Input {

    Schedule s = new Schedule();

    public static String put(String in) {
        String success = "";
        String[] temp = in.split("_");
        try {
            switch(temp[1]) {
                case "ADD":
                    return add(temp);
                case "REMOVE":
                    return remove(temp);
                case "VIEW":
                    return view(temp);
                case "VIEWCLASS":
                    return viewClass(temp);
                default:
                    throw new IncorrectActionException("The input is an invalid response from the client!");
            }
        }
        catch (IncorrectActionException e) {
            return e.getMessage();
        }
    }

    private static String add(String[] in) {

    }

    private static String remove(String[] in) {

    }

    private static String view(String[] in) {

    }

    private static String viewClass(String[] in) {

    }
}
