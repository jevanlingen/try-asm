package examine;

import java.util.Arrays;

public class CallLambdaExpressionByReflection {

    public static void main(String[] args) throws Throwable {
        // Print all methods
        var clazz = Lambda.class;
        var methods = clazz.getDeclaredMethods();
        Arrays.stream(methods).forEach(it -> System.out.println("METHOD: " + it.getName()));

        // Call the lambda //
        var add = Lambda.class.getDeclaredMethod("lambda$main$0", Integer.class);
        add.setAccessible(true);
        System.out.println(add.invoke(null, 3));
        System.out.println(add.invoke(null, 22));
    }
}
