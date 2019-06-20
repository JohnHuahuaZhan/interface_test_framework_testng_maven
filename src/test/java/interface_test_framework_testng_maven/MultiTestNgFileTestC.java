package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.test.CommonBase;
import interface_test_framework_testng_maven.util.common.TimeUtil;
import org.testng.annotations.*;

import java.util.Date;

public class MultiTestNgFileTestC extends CommonBase {
    @BeforeSuite
    public void multiTestNgFileTestC_bs(){

        System.out.printf("%s@%s#%s\n","multiTestNgFileTestC_bs",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeTest
    public void multiTestNgFileTestC_bt(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestC_bt",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeClass
    public void multiTestNgFileTestC_bc(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestC_bc",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeMethod
    public void multiTestNgFileTestC_bm(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestC_bm",Thread.currentThread().getId(), new Date().getTime());
    }
    @Test
    public void multiTestNgFileTestC(){
        System.out.printf("%s@%s#%s&%s^%s\n","multiTestNgFileTestC",Thread.currentThread().getId(), new Date().getTime(), ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }

    @Test
    public void multiTestNgFileTestC_1(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestC_1",Thread.currentThread().getId(), new Date().getTime());
        ContextManager.getInstance().getContext(getKey()).getProperty("login");
    }
}
