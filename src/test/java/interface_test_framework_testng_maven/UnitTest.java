package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import io.qameta.allure.Description;
import org.testng.annotations.Test;


public class UnitTest {


    @Test(description = "测试数据源工具类")
    public void testByteDataSource(){
        IByteDataSource byteDataSource = new StringPathFileByteDataSource("classpath:test/login/request.json", this.getClass());
        try {
            byte[] data = byteDataSource.getData();
            System.out.println(new String(data));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Description("测试csv共用数据驱动类")
    @CsvDataProvider(path = "classpath:test/login/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class)
    public void testCsvDataSource(String cell, String loginPassword, String selectedDefaultUserToLogin, String service){
        System.out.printf("%s, %s\n",cell, loginPassword);
    }

}