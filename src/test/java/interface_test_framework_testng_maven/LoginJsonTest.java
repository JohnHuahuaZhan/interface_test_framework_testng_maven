package interface_test_framework_testng_maven;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import interface_test_framework_testng_maven.guice.module.common.MarkerModule;
import interface_test_framework_testng_maven.network.MyRequest;
import interface_test_framework_testng_maven.network.MyResponse;
import interface_test_framework_testng_maven.template.IMarker;
import interface_test_framework_testng_maven.test.ClassLoadFileBase;
import interface_test_framework_testng_maven.test.process.IDeliverableHttpRequestProcessor;
import interface_test_framework_testng_maven.test.process.IHttpPrePostExceptionCallback;
import interface_test_framework_testng_maven.test.process.ListJSONHttpRequestPrePostExceptionProcessor;
import interface_test_framework_testng_maven.util.testng.TestContextUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Guice(modules = {MarkerModule.class})
@ByteDataSource(filePath = "classpath:test/login/request.json", charset = "utf-8")
public class LoginJsonTest extends ClassLoadFileBase implements IHttpPrePostExceptionCallback{

    private Map<String,String> allParameters;

    @Inject()
    @Named("Freemarker")
    IMarker marker;


    /**
     * path 在class中被覆盖
     * @param path
     */
    @BeforeClass
    @Parameters("path")
    public void parameters(String path, ITestContext context){
        allParameters = TestContextUtil.getTestParameter(context);
        allParameters.put("path", path);
    }

    @Description("json登陆测试很详细的细节描述偶")
    @Step("用户名 {cell} 密码 {loginPassword}")
    @CsvDataProvider(path = "classpath:test/login/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class, description = "json登陆测试")
    public void login(String cell, String loginPassword, String selectedDefaultUserToLogin, String service, ITestContext context){
        Map<String, String> interfaceInfo = TestContextUtil.getTestParameter(context);



        Map<String, String> parameter = new HashMap<>();
        parameter.put("cell", cell);
        parameter.put("loginPassword", loginPassword);
        parameter.put("selectedDefaultUserToLogin", selectedDefaultUserToLogin);
        parameter.put("service", service);



        Map<String, Object> map = new HashMap<>();
        //后来者覆盖前者
        map.putAll(allParameters);
        map.putAll(parameter);



        String json = getFileContent();
        try {
            json = marker.mark(json, map);

            IDeliverableHttpRequestProcessor prePostExceptionProcessor = new ListJSONHttpRequestPrePostExceptionProcessor(json, this, true);
            prePostExceptionProcessor.addDeliver(interfaceInfo);
            prePostExceptionProcessor.start();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(),throwable);
        }

    }

    @Override
    public void post(MyRequest request, MyResponse response, Map<String, String> deliver) {
        try {
            System.out.println(getContext().getProperty("host"));
            System.out.println(getContext().getNetworkConfig());
            System.out.println(response.string(deliver.get("responseCharset")));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
