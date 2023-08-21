package examine;

public class UseOfFunctionTwo {

    public static void main(String[] args) {
        var x = add(1, 3);
        var y = x + 100;
        var z = (x != -1) ? x + y : 23;
    }

    public static int add(final int a, final int b) {
        return a + b;
    }

}
