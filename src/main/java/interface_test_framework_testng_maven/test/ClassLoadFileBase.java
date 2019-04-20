package interface_test_framework_testng_maven.test;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import org.testng.annotations.BeforeClass;

public abstract class ClassLoadFileBase extends CommonBase {
    private String content;

    @BeforeClass
    protected  void class_load_file_base_bc_load_file() throws Throwable {
        ByteDataSource byteDataSource = this.getClass().getAnnotation(ByteDataSource.class);
        if(byteDataSource == null){
            throw new RuntimeException("当你的类继承ClassLoadFileBase，类必须用ByteDataSource注释");
        }

        String filePath = byteDataSource.filePath();
        Class cls = byteDataSource.cls();
        String charset = byteDataSource.charset();

        IByteDataSource dataSource = new StringPathFileByteDataSource(filePath, cls);
        content = new String(dataSource.getData(), charset);
    }

    public String getFileContent(){
        return content;
    }
}
