package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.ContextManager;
import org.testng.IAlterTestName;
import org.testng.IAttributes;
import org.testng.IClass;
import org.testng.ITest;
import org.testng.annotations.*;

import java.util.Date;

public class MultiTestNgFileTestA extends CommonBaseTest implements ITest {
    @BeforeSuite
    public void multiTestNgFileTestA_bs(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_bs",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeTest
    public void multiTestNgFileTestA_bt(){

        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_bt",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeClass
    public void multiTestNgFileTestA_bc(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_bc",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeClass
    public void multiTestNgFileTestA_bc_1(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_bc_1",Thread.currentThread().getId(), new Date().getTime());
    }
    @BeforeMethod
    public void multiTestNgFileTestA_bm(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_bm",Thread.currentThread().getId(), new Date().getTime());
    }
    @Test
    public void multiTestNgFileTestA(){
        System.out.printf("%s@%s#%s&%s^%s\n","multiTestNgFileTestA",Thread.currentThread().getId(), new Date().getTime(),  ContextManager.getInstance().getContext(getKey()).networkConfig,ContextManager.getInstance().getContext(getKey()).mapProperties);
    }
    @Test
    public void multiTestNgFileTestA_1(){
        System.out.printf("%s@%s#%s\n","multiTestNgFileTestA_1",Thread.currentThread().getId(), new Date().getTime());
    }

    @Override
    public String getTestName() {
        return null;
    }
}
