package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.test.CommonBase;
import org.testng.annotations.Test;

public class MultiTestNgFileTestE  extends CommonBase {

    @Test
    public void multiTestNgFileTestE(){
        System.out.printf("%s@%s&%s^%s\n","multiTestNgFileTestE",Thread.currentThread().getId(), ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }
}
