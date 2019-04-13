package interface_test_framework_testng_maven.data.test_data;

import java.util.Iterator;

public interface ITestDataSource {
    Iterator<Object[]> getData() throws Throwable;
}
