package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.test.CommonBase;
import org.testng.annotations.Test;

public class MultiTestNgFileTestF extends CommonBase {

    @Test
    public void multiTestNgFileTestF(){
        System.out.printf("%s@%s&%s^%s\n","multiTestNgFileTestF",Thread.currentThread().getId(), ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }
}
