package interface_test_framework_testng_maven.test;

import interface_test_framework_testng_maven.context.Context;
import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.util.testng.TestContextUtil;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

public class Base {

    private String key;
    @BeforeClass
    protected void base_before_class_get_context(ITestContext context){
        this. key = TestContextUtil.getSuiteName(context);
    }

    public String getKey() {
        return key;
    }
}
