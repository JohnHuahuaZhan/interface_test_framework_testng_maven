package interface_test_framework_testng_maven.juice.add;


import com.google.inject.ImplementedBy;



@ImplementedBy(SimpleAdd.class)
public interface Add {
    int add(int a, int b);
}
