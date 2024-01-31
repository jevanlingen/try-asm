package examine;

public class SimpleFunctionsTwo {

    public static boolean doReverse(boolean a) {
        if (a) {
            return false;
        }
        return true;
    }

    public static boolean doReverse2(boolean a) {
        return !a;
    }

    public static String originalSwitch(int a) {
        switch (a) {
            case 1: return "One";
            case 2:
            case 3: return "Two or Three";
            case 4: return "Four";
            default: return "More than four";
        }
    }

    public static String newSwitch(int a) {
        return switch (a) {
            case 1 -> "One";
            case 2, 3 -> "Two or Three";
            case 4 -> "Four";
            default -> "More than four";
        };
    }
}
