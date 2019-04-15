package interface_test_framework_testng_maven.juice.add;

public class OtherSimpleAdd implements Add{

    @Override
    public int add(int a, int b) {
        System.out.println("other");return a + b;
    }
}
