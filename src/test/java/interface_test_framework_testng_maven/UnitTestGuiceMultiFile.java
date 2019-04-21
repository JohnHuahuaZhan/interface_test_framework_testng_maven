package interface_test_framework_testng_maven;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSources;
import interface_test_framework_testng_maven.guice.module.annotation.GuiceByteDataSources;
import interface_test_framework_testng_maven.guice.module.factory.ByteDataSourcesModuleFactory;
import interface_test_framework_testng_maven.template.IMarker;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Map;

@GuiceByteDataSources(filePaths = {"classpath:test/base/this.txt", "classpath:test/base/other.txt"})
@Guice(moduleFactory = ByteDataSourcesModuleFactory.class)
public class UnitTestGuiceMultiFile {

    @Inject @Named("FilePath")
    Map<String, IByteDataSource> byteDataSources;


    @Test
    public void testGuice() throws Throwable {
        for (Map.Entry<String, IByteDataSource> stringIByteDataSourceEntry : byteDataSources.entrySet()) {
            byte[] data = stringIByteDataSourceEntry.getValue().getData();
            String json = new String(data);
            System.out.printf("%s,%s@%s\n",stringIByteDataSourceEntry.getKey(), json, Thread.currentThread().getName());
        }

    }
}