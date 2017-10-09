package settings;

/**
 * @author by Ilin_ai on 07.10.2017.
 */
public enum Datatype {
    LONG(Long.class),
    DOUBLE(Double.class),
    DURATION(String.class),
    STRING(String.class);

    private Class aClass;

    Datatype(Class aClass){
        this.aClass = aClass;
    }

    public Class getaClass() {
        return aClass;
    }
}
