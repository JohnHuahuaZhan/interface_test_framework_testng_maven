package interface_test_framework_testng_maven.util.type;

public class KeyValue<KEY, VALUE> {
    KEY key;
    VALUE value;

    public KeyValue(KEY key, VALUE value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

    public KEY getKey() {
        return key;
    }

    public void setKey(KEY key) {
        this.key = key;
    }

    public VALUE getValue() {
        return value;
    }

    public void setValue(VALUE value) {
        this.value = value;
    }
}
