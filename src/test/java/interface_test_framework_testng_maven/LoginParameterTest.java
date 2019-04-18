package interface_test_framework_testng_maven;

import com.jayway.jsonpath.JsonPath;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import interface_test_framework_testng_maven.network.util.HttpRequestUtil;
import interface_test_framework_testng_maven.util.testng.TestContextUtil;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginParameterTest {
    private Map<String,String> allParameters;
    /**
     * path port 在class中被覆盖
     * @param path
     */
    @BeforeClass
    @Parameters({"path", "port"})
    public void parameters(String path, String port, ITestContext context){
        allParameters = TestContextUtil.getTestParameter(context);
        allParameters.put("path", path);
        allParameters.put("port", port);
    }

    @CsvDataProvider(path = "classpath:test/login/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class, description = "登陆测试")
    public void login(String cell, String loginPassword, String selectedDefaultUserToLogin, String service, ITestContext context){
        Map<String, String> parameter = new HashMap<>();
        parameter.put("cell", cell);
        parameter.put("loginPassword", loginPassword);
        parameter.put("selectedDefaultUserToLogin", selectedDefaultUserToLogin);
        parameter.put("service", service);

        try {
            byte[] bytes = HttpRequestUtil.post(
                    allParameters.get("scheme"),
                    allParameters.get("host"),
                    allParameters.get("path"),
                    allParameters.get("port"),
                    allParameters.get("requestCharset"),
                    parameter
                    );

            String result = new String(bytes, allParameters.get("responseCharset"));

            Assert.assertEquals(JsonPath.read(result, "$.response.success"), Boolean.valueOf(true), result);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        System.out.println();
    }
}
