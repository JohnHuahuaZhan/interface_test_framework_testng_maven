package interface_test_framework_testng_maven.init;

import interface_test_framework_testng_maven.context.Context;
import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.init.observer.RequestSignObserver;
import interface_test_framework_testng_maven.util.testng.TestContextUtil;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
public class Init {

    @BeforeSuite(description = "初始化测试上下文")
    public void beforeSuite(ITestContext iTestContext){
        String suiteName = TestContextUtil.getSuiteName(iTestContext);
        Context context = new Context(suiteName, suiteName);
        context.init();//初始化
        context.addObserver(Context.REQUEST_PARSED_TYPE, new RequestSignObserver());//订阅消息
        ContextManager.getInstance().addContext(suiteName, context);
    }
}
