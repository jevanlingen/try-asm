package examine;

public class SimpleFunctions {

    public static int add(final int a, final int b) {
        return a + b;
    }

    public static int addWithoutFinal(int a, int b) {
        return a + b;
    }

    public static String s() {
        return "Hello World";
    }

    public static boolean doReverse(boolean a) {
        if (a) {
            return false;
        }
        return true;
    }

    public static boolean doReverse2(boolean a) {
        return !a;
    }

    public static boolean showUs() {
        return true;
    }

}
