package interface_test_framework_testng_maven.test.process;


import interface_test_framework_testng_maven.network.MyRequest;
import interface_test_framework_testng_maven.network.MyResponse;

import java.util.Map;

public interface IHttpPrePostExceptionCallback {
    default String preParse(String source) { return source; }
    default void pre(MyRequest request, MyResponse response, Map<String, String> deliver){}
    default void post(MyRequest request, MyResponse response, Map<String, String> deliver){}
    default void exception(MyRequest request, Exception e, Map<String, String> deliver){ throw new RuntimeException(e.getMessage(), e);}
}
