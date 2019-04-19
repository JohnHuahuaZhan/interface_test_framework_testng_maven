package interface_test_framework_testng_maven;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSource;
import interface_test_framework_testng_maven.guice.module.factory.ByteDataSourceModuleFactory;
import interface_test_framework_testng_maven.template.IMarker;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@ByteDataSource(filePath = "classpath:test/login/this.txt")
@Guice(moduleFactory = ByteDataSourceModuleFactory.class)
public class UnitTestGuice {

    @Inject @Named("FilePath")
    IByteDataSource byteDataSource;


    @Inject @Named("Freemarker")
    IMarker marker;

    @Test
    public void testGuice() throws Throwable {
        byte[] data = byteDataSource.getData();
        String json = new String(data);
        System.out.println(json);
    }
}