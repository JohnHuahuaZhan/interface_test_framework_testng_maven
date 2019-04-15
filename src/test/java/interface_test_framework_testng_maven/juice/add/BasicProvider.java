package interface_test_framework_testng_maven.juice.add;

import com.google.inject.Provider;

public class BasicProvider implements Provider<Add> {
    @Override
    public Add get() {
        System.out.println("in BasicProvider");
        return new SimpleAdd();
    }
}
