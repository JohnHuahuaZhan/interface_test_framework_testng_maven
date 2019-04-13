package interface_test_framework_testng_maven.network;

public class MultiText {
    String value;
    String mime;

    public MultiText(String value, String mime) {
        this.value = value;
        this.mime = mime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
