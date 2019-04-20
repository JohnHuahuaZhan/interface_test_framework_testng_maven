package interface_test_framework_testng_maven;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jayway.jsonpath.JsonPath;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.guice.module.annotation.GuiceByteDataSource;
import interface_test_framework_testng_maven.guice.module.factory.ByteDataSourceModuleFactory;
import interface_test_framework_testng_maven.network.util.HttpRequestUtil;
import interface_test_framework_testng_maven.test.CommonBase;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static com.github.dreamhead.moco.MocoJsonRunner.jsonHttpServer;
import static com.github.dreamhead.moco.Runner.runner;
import static com.github.dreamhead.moco.Moco.json;


@GuiceByteDataSource(filePath = "classpath:test/request/mock.json")
@Guice(moduleFactory = ByteDataSourceModuleFactory.class)
public class RequestViaTestNGParameterTest extends CommonBase {
    //注入数据源
    @Inject
    @Named("FilePath")
    IByteDataSource byteDataSource;


    private Runner runner;

    //mock服务，调试的时候用。可以删除后用正式的接口
    @BeforeClass
    public void startMock() throws Throwable {
        byte[] data = byteDataSource.getData();
        String json = new String(data);

        HttpServer server = jsonHttpServer(12306, json(json));
        runner = runner(server);
        runner.start();

    }


    @Test(description = "通过testNG参数获取接口信息测试")
    public void login(){
        Map<String, String> allParameters = getAllParameters();

        Map<String, String> parameter = new HashMap<>();
        parameter.put("cell", allParameters.get("cell"));
        parameter.put("password",  allParameters.get("password"));


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

            System.out.println(result);
            AssertJUnit.assertEquals("登录",JsonPath.<Object>read(result, "$.success"), true);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @AfterClass
    public void stopMock(){
        runner.stop();
    }
}
