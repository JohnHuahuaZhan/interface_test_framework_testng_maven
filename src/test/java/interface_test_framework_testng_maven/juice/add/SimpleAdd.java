package interface_test_framework_testng_maven.juice.add;


import com.google.inject.Singleton;

public class SimpleAdd implements Add{

    @Override
    public int add(int a, int b) {
        System.out.println("basic");
        return a + b;
    }
}
