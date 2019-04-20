package interface_test_framework_testng_maven.guice.module.factory;

import com.google.inject.Module;
import interface_test_framework_testng_maven.data.annotation.ByteDataSources;
import interface_test_framework_testng_maven.guice.module.ByteDataSourcesModule;
import interface_test_framework_testng_maven.guice.module.annotation.GuiceByteDataSources;
import org.testng.IModuleFactory;
import org.testng.ITestContext;

public class ByteDataSourcesModuleFactory implements IModuleFactory {
    @Override
    public Module createModule(ITestContext context, Class<?> testClass) {
        ByteDataSourcesModule module = new ByteDataSourcesModule();
        GuiceByteDataSources byteDataSource = testClass.getAnnotation(GuiceByteDataSources.class);
        if(null != byteDataSource){
            module.setFilePaths(byteDataSource.filePaths());
            module.setFileNames(byteDataSource.fileNames());
            module.setClasses(byteDataSource.classes());
            module.setCharsets(byteDataSource.charsets());
        }
        return module;
    }
}
