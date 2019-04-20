package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSources;
import interface_test_framework_testng_maven.test.ClassLoadFileBase;
import interface_test_framework_testng_maven.test.MultiClassLoadFileBase;
import org.testng.annotations.Test;


@ByteDataSources(filePaths = {"classpath:test/base/this.txt", "classpath:test/base/other.txt"}, fileNames = {"json", "csv"})
public class ClassLoadMultiFileBaseTest extends MultiClassLoadFileBase {
    @Test(description = "测试多文件自动加载基类")
    public void test(){
        System.out.println(getFileContent());
    }
}
