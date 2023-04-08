import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class UseAsm {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        // boolean can be found
        final var aNewBooleanField = UseAsm.class.getField("aNewBooleanField").getBoolean(null);
        System.out.println(aNewBooleanField);


        // private method can be called, because it's made public
        UseAsm.class.getMethod("shouldNotBePublic").invoke(null);


        // Shows all interfaces
        System.out.println(Arrays.toString(UseAsm.class.getInterfaces()));


        // Call add method
        var add = UseAsm.class.getDeclaredMethod("add", int.class, int.class);
        System.out.println(add.invoke(null, 3 , 4));


        // Call doReverse method
        var doReverse = UseAsm.class.getDeclaredMethod("doReverse", boolean.class);
        System.out.println(doReverse.invoke(null, true));
        System.out.println(doReverse.invoke(null, false));


        // Call fib method
        var fib = UseAsm.class.getDeclaredMethod("fib", float.class);
        System.out.println(fib.invoke(null, 3));
    }

    private static void shouldNotBePublic() {
        System.out.println("haha!");
    }
}
