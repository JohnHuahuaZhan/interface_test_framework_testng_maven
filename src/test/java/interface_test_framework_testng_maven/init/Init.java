package interface_test_framework_testng_maven.init;

import interface_test_framework_testng_maven.context.Context;
import org.testng.annotations.BeforeSuite;

public class Init {
    @BeforeSuite(description = "初始化测试上下文")
    public void beforeSuite(){
        System.out.println("初始化测试上下文");
        Context.getInstance().init();
    }
}
