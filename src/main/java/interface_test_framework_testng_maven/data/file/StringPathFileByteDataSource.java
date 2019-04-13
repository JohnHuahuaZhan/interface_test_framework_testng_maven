package interface_test_framework_testng_maven.data.file;

import interface_test_framework_testng_maven.data.IByteDataSource;


/**
 * * class:path
 *  * classpath:path
 *  * pure:path
 */
public class StringPathFileByteDataSource implements IByteDataSource{
    byte[] content;//缓存

    private String path;
    private Class cls;
    private IByteDataSource byteDataSource;

    public StringPathFileByteDataSource(String path, Class cls) {
        this.path = path;
        this.cls = cls;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void update(){
        IFileInputStreamBuilderParser parser = new StringPathParser(path, cls);
        byteDataSource = parser.get().build();
    }

    @Override
    public byte[] getData() throws Throwable {
        if(null == byteDataSource)
            update();

        if(null == content)
            content = byteDataSource.getData();
        return content;
    }
}
