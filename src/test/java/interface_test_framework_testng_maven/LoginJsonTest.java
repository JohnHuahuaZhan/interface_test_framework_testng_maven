package interface_test_framework_testng_maven;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.annotation.IgnoreNamedParam;
import interface_test_framework_testng_maven.annotation.NamedParam;
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
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.ITestContext;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Guice(modules = {MarkerModule.class})
@ByteDataSource(filePath = "classpath:test/login/request.json", charset = "utf-8")
public class LoginJsonTest extends ClassLoadFileBase implements IHttpPrePostExceptionCallback{


    @Inject
    @Named("Freemarker")
    IMarker marker;




    @Description("json登陆测试很详细的细节描述偶")
    @Step("用户名 {cell} 密码 {loginPassword}")
    @CsvDataProvider(path = "classpath:test/login/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class, description = "json登陆测试")
    public void login(@NamedParam("cell") String cell,
                      @NamedParam("loginPassword")String loginPassword,
                      @NamedParam("selectedDefaultUserToLogin")String selectedDefaultUserToLogin,
                      @NamedParam("service")String service,
                      @IgnoreNamedParam ITestContext context){




        Map<String, Object> map = new HashMap<>();
        //后来者覆盖前者
        map.putAll(super.getAllParameters());
        map.putAll(super.getParams());



        String json = getFileContent();
        try {
            json = marker.mark(json, map);

            IDeliverableHttpRequestProcessor prePostExceptionProcessor = new ListJSONHttpRequestPrePostExceptionProcessor(json, this, true);
            prePostExceptionProcessor.addDeliver(super.getAllParameters());
            prePostExceptionProcessor.start();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(),throwable);
        }

    }

    @Override
    public void post(MyRequest request, MyResponse response, Map<String, String> deliver) {
        try {
            getContext().getProperty("host");
            getContext().getNetworkConfig();
            System.out.println(response.string(deliver.get("responseCharset")));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
