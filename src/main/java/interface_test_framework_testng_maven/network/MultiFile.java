package interface_test_framework_testng_maven.network;

public class MultiFile {
    String fileName;
    byte[] content;
    String mime;

    public MultiFile(String fileName, byte[] content, String mime) {
        this.fileName = fileName;
        this.content = content;
        this.mime = mime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
