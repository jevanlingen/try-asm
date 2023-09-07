package examine;

import examine.some.deeply.nested.packagename.Whatever;

public class SimpleClass {

    private int anInt;
    private boolean aBoolean;
    private double aDouble;
    private long aLong;
    private float aFloat;
    private Object anObject;
    private Whatever anImportedObject;

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}
