package interface_test_framework_testng_maven;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.annotation.IgnoreNamedParam;
import interface_test_framework_testng_maven.annotation.NamedParam;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import interface_test_framework_testng_maven.guice.module.annotation.GuiceByteDataSource;
import interface_test_framework_testng_maven.guice.module.factory.ByteDataSourceModuleFactory;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.dreamhead.moco.Moco.json;
import static com.github.dreamhead.moco.MocoJsonRunner.jsonHttpServer;
import static com.github.dreamhead.moco.Runner.runner;


@ByteDataSource(filePath = "classpath:test/request/request.json", charset = "utf-8")
@GuiceByteDataSource(filePath = "classpath:test/request/mock.json")
@Guice(moduleFactory = ByteDataSourceModuleFactory.class)
public class RequestViaCsvTest extends ClassLoadFileBase implements IHttpPrePostExceptionCallback{
    @Inject
    @Named("Freemarker")
    IMarker marker;

    @Inject
    @Named("FilePath")
    IByteDataSource byteDataSource;

    private Runner runner;

    //mock服务，调试的时候用。可以删除后用正式的接口
    @BeforeClass
    public void startMock() throws Throwable {
        byte[] data = byteDataSource.getData();
        String json = new String(data);

        HttpServer server = jsonHttpServer(12307, json(json));
        runner = runner(server);
        runner.start();
    }

    @Description("json登陆测试很详细的细节描述偶")
    @Step("用户名 {cell} 密码 {password}")
    @CsvDataProvider(path = "classpath:test/request/data.csv")
    @Test(dataProvider = "csv", dataProviderClass = DataProviders.class, description = "json登陆测试")
    public void login(@NamedParam("cell") String cell,
                      @NamedParam("password")String password,
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

    @AfterClass(alwaysRun = true)
    public void stopMock(){
        runner.stop();
    }
}
