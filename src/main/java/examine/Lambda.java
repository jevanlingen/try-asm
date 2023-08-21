package examine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Lambda {

    public static void main(String[] args) {
        Function<Integer, Boolean> isOdd = (x) -> x % 2 == 0;
        Function<List<String>, Optional<String>> combineWords = (list) -> list.stream().reduce((a, b) -> a + b);
    }
}
