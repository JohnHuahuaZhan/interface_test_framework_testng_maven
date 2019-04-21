package interface_test_framework_testng_maven.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextManager {
    private static ContextManager ourInstance = new ContextManager();

    public static ContextManager getInstance() {
        return ourInstance;
    }

    private ContextManager() {
    }

    Map<String, Context> map = new ConcurrentHashMap<>();



    public void addContext(String key, Context context){
        map.put(key, context);
    }

    public Context getContext(String key){
        return map.get(key);
    }
}
