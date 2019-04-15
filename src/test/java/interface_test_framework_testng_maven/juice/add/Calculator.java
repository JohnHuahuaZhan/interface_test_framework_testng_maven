package interface_test_framework_testng_maven.juice.add;

import com.google.inject.Inject;

public class Calculator {

    @Inject
    private Add add;

    public int add(int a, int b){
        System.out.println(add);
        return add.add(a, b);
    }
}
