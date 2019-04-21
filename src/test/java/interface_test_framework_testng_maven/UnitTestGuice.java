package interface_test_framework_testng_maven;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.guice.module.annotation.GuiceByteDataSource;
import interface_test_framework_testng_maven.guice.module.factory.ByteDataSourceModuleFactory;
import interface_test_framework_testng_maven.template.IMarker;
import interface_test_framework_testng_maven.util.type.KeyValue;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@GuiceByteDataSource(filePath = "classpath:test/base/mark.json")
@Guice(moduleFactory = ByteDataSourceModuleFactory.class)
public class UnitTestGuice {

    //注入数据源
    @Inject @Named("FilePath")
    IByteDataSource byteDataSource;


    //注入Freemarker渲染引擎
    @Inject @Named("Freemarker")
    IMarker marker;

    @Test(description = "guice注入测试")
    public void testGuice() throws Throwable {
        byte[] data = byteDataSource.getData();
        String json = new String(data);
        json = marker.mark(json, new KeyValue<String, Object>("name", "Trump"));
        System.out.println(json+"@"+Thread.currentThread().getName());
    }
}