package interface_test_framework_testng_maven.network;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyClientManager {
    private static MyClientManager ourInstance = new MyClientManager();

    public static MyClientManager getInstance() {
        return ourInstance;
    }

    private MyClientManager() {
    }

    Map<String, MyHttpClient> map = new ConcurrentHashMap<>();



    public void addClient(String key, MyHttpClient client){
        map.put(key, client);
    }

    public MyHttpClient getClient(String key){
        return map.get(key);
    }
}
