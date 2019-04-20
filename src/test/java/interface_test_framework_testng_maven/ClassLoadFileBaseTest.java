package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.test.ClassLoadFileBase;
import org.testng.annotations.Test;


@ByteDataSource(filePath = "classpath:test/base/this.txt", charset = "utf-8")
public class ClassLoadFileBaseTest extends ClassLoadFileBase {
    @Test(description = "测试单文件自动加载基类")
    public void test(){
        System.out.println(getFileContent());
    }
}
