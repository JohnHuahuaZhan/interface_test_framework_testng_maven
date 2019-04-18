package interface_test_framework_testng_maven.test;

import interface_test_framework_testng_maven.context.Context;

public class Base {
    protected Context context = Context.getInstance();

    protected Context getContext(){
        return context;
    }
}
