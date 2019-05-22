package interface_test_framework_testng_maven.listeners;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import io.qameta.allure.Attachment;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import java.util.Date;

public class CommonHookListener implements IHookable {
    @Attachment(value = "测试通过", type = "text/plain")
    public String pass() throws Throwable {
        return new Date().toString();
    }
    @Attachment(value = "测试失败", type = "text/plain")
    public String failed() {
        return "用例失败了，如果是selenium测试，你可以在这里附加浏览器的截图";
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            failed();
        }else {
            try {
                pass();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
