package interface_test_framework_testng_maven.juice.add;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.Test;

public class AddTest {

    @Test
    public void test(){
        Injector injector = Guice.createInjector();
        Calculator a = injector.getInstance(Calculator.class);
        System.out.println(a.add(2,6));
        injector.injectMembers(a);
        System.out.println(a.add(2,6));
    }
}
