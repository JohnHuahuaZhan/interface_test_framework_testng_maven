package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.test.CommonBase;
import org.testng.annotations.Test;

public class MultiTestNgFileTestD extends CommonBase {

    @Test
    public void multiTestNgFileTestD(){
        System.out.printf("%s@%s&%s^%s\n","multiTestNgFileTestD",Thread.currentThread().getId(), ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }
}
