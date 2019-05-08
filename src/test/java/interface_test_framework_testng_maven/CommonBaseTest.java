package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.annotation.IgnoreNamedParam;
import interface_test_framework_testng_maven.annotation.NamedParam;
import interface_test_framework_testng_maven.annotation.Scenario;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import interface_test_framework_testng_maven.test.ClassLoadFileBase;
import interface_test_framework_testng_maven.test.CommonBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;


@Scenario("公共基础基类正常场景测试")
public class CommonBaseTest extends CommonBase {
    @Test(description = "测试公共基础基类获取方法名")
    public void it_is_a_beautiful_test_method_name(){
        System.out.println(getMethodName());
    }

    @Test(description = "测试公共基础基类获取testNG文件配置参数")
    public void testGetTestNGParameter(){
        System.out.println(getAllParameters());
    }



    @Test(description = "测试scenario 测试方法没有参数")
    public void scenario(){

    }



    @Description("测试公共基础基类获取当前方法参数map")
    @CsvDataProvider(path = "classpath:test/base/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class)
    public void testGetMethodParamMap(
            @IgnoreNamedParam String memo,
            @NamedParam("cell") String cell,
            @NamedParam("loginPassword") String loginPassword,
            @NamedParam("selectedDefaultUserToLogin") String selectedDefaultUserToLogin,
            @NamedParam("service") String service){
        System.out.println(getParams());
    }
}
