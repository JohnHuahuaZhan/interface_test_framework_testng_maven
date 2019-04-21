package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.test.CommonBase;
import interface_test_framework_testng_maven.util.common.TimeUtil;
import org.testng.annotations.*;

import java.util.Date;

public class MultiTestNgFileTestB extends CommonBase {
    @BeforeSuite
    public void multiTestNgFileTestB_bs(){

        System.out.printf("%s@%s#%s\n","multiTestNgFileTestB_bs",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeTest
    public void multiTestNgFileTestB_bt(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestB_bt",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeClass
    public void multiTestNgFileTestB_bc(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestB_bc",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeMethod
    public void multiTestNgFileTestB_bm(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestB_bm",Thread.currentThread().getId(), new Date().getTime());
    }
    @Test
    public void multiTestNgFileTestB(){
        System.out.printf("%s@%s#%s&%s^%s\n","multiTestNgFileTestB",Thread.currentThread().getId(), new Date().getTime(), ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }
    @Test
    public void multiTestNgFileTestB_1(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestB_1",Thread.currentThread().getId(), new Date().getTime());
    }
}
