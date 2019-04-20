package interface_test_framework_testng_maven.template;

import interface_test_framework_testng_maven.util.type.KeyValue;

import java.util.Map;

public interface IMarker {
    /**
     * 默认原样返回
     * @param source
     * @param map
     * @return
     * @throws Throwable
     */
    default String mark(String source, Map<String, Object> map) throws Throwable {
        return source;
    };


    /**
     * 默认原样返回
     * @param source
     * @param kvs
     * @return
     * @throws Throwable
     */
    default String mark(String source, KeyValue<String, Object>... kvs) throws Throwable {
        return source;
    };
}
