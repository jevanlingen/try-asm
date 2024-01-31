package examine;

public class UseOfFunctionTwo {

    public static void main(String[] args) {
        var v = add(1, 3);
        var w = v + 100;
        var x = (v != -1) ? v + w : 23;
        var y = x + 1000;
        var z = x + 1000000;
    }

    public static int add(final int a, final int b) {
        return a + b;
    }

}
