package interface_test_framework_testng_maven.juice.add;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Set;

public class Calculator {

    @Inject
    private Set<String> currencies;

    @Named("id")
    @Inject
    private Long id;

    @Inject
    private Add add;

    public int add(int a, int b){
        return add.add(a, b);
    }
}
