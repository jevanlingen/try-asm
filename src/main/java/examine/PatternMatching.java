package examine;

public class PatternMatching {

    public static void main(String[] args) {
        var x = 32;

        Object o = 23; // any object
        String formatter = switch(o) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case Object so  -> String.format("Object %s", so.toString());
        };
    }
}
