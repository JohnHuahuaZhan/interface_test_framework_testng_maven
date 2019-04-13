package interface_test_framework_testng_maven.data.test_data;


import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;

import java.io.IOException;
import java.util.Iterator;

public class CsvFileTestDataSource extends CsvContentTestDataSource {
    private  Class  cls;
    private  String path;
    private  String charset;

    public CsvFileTestDataSource(Class cls, String path, String charset) {
        super(new StringPathFileByteDataSource(path, cls), charset);
        this.cls = cls;
        this.path = path;
        this.charset = charset;

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

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public CsvFileTestDataSource(String path ,Class cls) {
        this(cls, path, "utf-8");
    }

    //可以多次获取，因为依赖了支持缓存的StringPathFileByteDataSource
    @Override
    public Iterator<Object[]> getData() throws IOException {
        if(null == cls || path == null)
            throw new IOException("类或者文件路径没有指定");
        return super.getData();
    }
}
