package interface_test_framework_testng_maven.init;

import interface_test_framework_testng_maven.context.Context;
import interface_test_framework_testng_maven.init.observer.RequestSignObserver;
import org.testng.annotations.BeforeSuite;

import java.util.Date;

public class Init {
    @BeforeSuite(description = "初始化测试上下文")
    public void beforeSuite(){
        Context.getInstance().init();//初始化
        Context.getInstance().addObserver(Context.REQUEST_PARSED_TYPE, new RequestSignObserver());//订阅消息
    }
}
