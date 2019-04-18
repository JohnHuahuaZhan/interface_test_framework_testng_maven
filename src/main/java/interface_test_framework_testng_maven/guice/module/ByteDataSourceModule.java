package interface_test_framework_testng_maven.guice.module;

import com.google.inject.name.Names;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;

public class ByteDataSourceModule extends InstallCommonModule {

    /**
     * for file source
     */
    String filePath = "";
    Class cls = ByteDataSourceModule.class;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }



    @Override
    protected void config() {
        //StringPathFile
        bind(IByteDataSource.class).annotatedWith(Names.named("FilePath")).toInstance(new StringPathFileByteDataSource(filePath, cls));
    }
}
