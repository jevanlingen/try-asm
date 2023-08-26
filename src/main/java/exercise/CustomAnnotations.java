package exercise;

// 1. Enable annotation
// @Getter
// 2. Enable annotation (create annotation yourself)
// @Setter
// 3. Enable annotation (create annotation yourself)
// @AllArgsConstructor
public class CustomAnnotations {
    private String foo;
    private boolean bar;

    // 3.1 Remove constructor
    public CustomAnnotations(final String foo, final boolean bar) {
        this.foo = foo;
        this.bar = bar;
    }

    // 1.1 Remove this getter
    public String getFoo() {
        return foo;
    }

    // 1.2 Remove this getter
    public boolean isBar() {
        return bar;
    }

    // 2.1 Remove this setter
    public void setFoo(final String foo) {
        this.foo = foo;
    }

    // 2.2 Remove this setter
    public void setBar(final boolean bar) {
        this.bar = bar;
    }
}
