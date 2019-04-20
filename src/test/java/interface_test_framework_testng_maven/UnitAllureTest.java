package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class UnitAllureTest {


    @Test(description = "分几个步骤的测试")
    public void testByteDataSource() throws Throwable {
        step1Logic();

            saveScreenshot();
            step2Logic();
            saveScreenshot();

        step3Logic();
    }

    @BeforeMethod
    public void bm() throws Throwable {
        saveScreenshot();
    }
    @Attachment(value = "这是美女截图", type = "image/jpg")
    public byte[] saveScreenshot() throws Throwable {
        return new StringPathFileByteDataSource("classpath:test/base/jpg.jpg",this.getClass()).getData();
    }


    @Step("This is step 1")
    private void step1Logic() {
        // Step1 implementation
    }

    @Step("This is step 2")
    private void step2Logic() {
        int c = 1 / 0;//跑个异常
    }
    @Step("This is step 3")
    private void step3Logic() {

    }
}