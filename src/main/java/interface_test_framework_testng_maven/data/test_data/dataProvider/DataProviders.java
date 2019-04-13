package interface_test_framework_testng_maven.data.test_data.dataProvider;

import interface_test_framework_testng_maven.data.test_data.CsvFileTestDataSource;
import interface_test_framework_testng_maven.data.test_data.ITestDataSource;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;

public class DataProviders {
    @DataProvider(name = "csv")
    public static Iterator<Object[]> csvData(Method method) throws Throwable {
        CsvDataProvider dataProviderParams = method.getAnnotation(CsvDataProvider.class);
        if (null == dataProviderParams) {
            throw new IllegalArgumentException(" must use together with @CsvDataProvider.");
        }

        if ("".equals(dataProviderParams.path())) {
            throw new IllegalArgumentException("@CsvDataProvider path can not be empty.");
        }


        String csvDirectory = dataProviderParams.path();
        String charset = dataProviderParams.charset();
        Class cls = dataProviderParams.cls();
        ITestDataSource testDataSource = new CsvFileTestDataSource(cls,csvDirectory,charset);
        return testDataSource.getData();
    }
}
