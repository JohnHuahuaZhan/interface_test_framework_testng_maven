package interface_test_framework_testng_maven.listeners;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class CommonHookListener implements IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
//        System.out.println("before hook:"+testResult.getMethod().getMethodName());
        callBack.runTestMethod(testResult);
//        System.out.println("after hook:"+testResult.getMethod().getMethodName());
    }
}
