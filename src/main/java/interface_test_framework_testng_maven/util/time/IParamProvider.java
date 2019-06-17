package interface_test_framework_testng_maven.util.time;

public interface IParamProvider<R, T> {
    R get(T p);
}
