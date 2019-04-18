package interface_test_framework_testng_maven.util.testng;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlClass;

import java.util.Map;

public class TestContextUtil {
    public static Map<String, String> getTestParameter(ITestContext context){
            return context.getCurrentXmlTest().getAllParameters();
    }

    /**
     * 不要在同一个test里面声明同一个类
     * @param result
     * @return
     */
    public static Map<String, String> getClassParameter(ITestResult result){
        XmlClass cls = result.getTestClass().getXmlClass();
        Map<String, String> mapClass = cls.getAllParameters();
        return mapClass;
    }
}
