package interface_test_framework_testng_maven.network;

public class Raw {
    byte[] value;
    String mime;

    public Raw() {
    }

    public Raw(byte[] value, String mime) {
        this.value = value;
        this.mime = mime;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
