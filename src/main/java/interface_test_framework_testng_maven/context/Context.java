package interface_test_framework_testng_maven.context;

import interface_test_framework_testng_maven.context.config.NetworkConfig;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.network.MyClientManager;
import interface_test_framework_testng_maven.network.MyHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Context {

    public static final String REQUEST_PARSED_TYPE = "REQUEST_PARSED_TYPE";

    private String key;
    private String pathPrefix;

    public Context(String key, String pathPrefix) {
        this.key = key;
        this.pathPrefix = pathPrefix;
    }

    private ContextObservable contextObservable = new ContextObservable();

    public NetworkConfig networkConfig = new NetworkConfig();

    public Map<String,String> mapProperties = new HashMap<>();

    public void init(){
        loadProperties();
        loadNextWorkConfig();
    }
    public void loadProperties(){
        URL url  = Context.class.getResource(String.format("/%s/%s", pathPrefix, "context"));
        if(null == url){
            throw new RuntimeException(String.format("/%s/%s 不存在", pathPrefix, "context"));
        }
        String classpath = url.getPath();
        if(StringUtils.startsWithAny(classpath,"/","\\") && System.getProperty("os.name").toLowerCase().startsWith("win")){
            classpath = StringUtils.substring(classpath, 1);
        }

        File file = new File(classpath);

        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".properties");
            }
        });

        for (int i = 0; i < files.length; i++) {
            try(InputStream inputStream = new FileInputStream(files[i])) {
                Properties properties = new Properties();
                properties.load(inputStream);

                Set<String> set = properties.stringPropertyNames();
                for (String s : set) {
                    mapProperties.put(s, properties.getProperty(s));
                }
            }catch (IOException e){
                throw new RuntimeException(e.getMessage(),e);
            }
        }

    }
    public void loadNextWorkConfig(){
        try{

            IByteDataSource byteDataSource = new StringPathFileByteDataSource(String.format("classpath:%s/%s/network.json", pathPrefix,"context"), this.getClass());
            String json = new String(byteDataSource.getData(), "utf-8");

            JSONObject root = new JSONObject(json);

            networkConfig.connectTimeout = Integer.valueOf(root.getInt("connectTimeout"));
            networkConfig.writeTimeout = Integer.valueOf(root.getInt("writeTimeout"));
            networkConfig.readTimeout = Integer.valueOf(root.getInt("readTimeout"));

            MyHttpClient myHttpClient = new MyHttpClient(this.key);
            myHttpClient.setConnectTimeout(networkConfig.connectTimeout);
            myHttpClient.setReadTimeout(networkConfig.readTimeout);
            myHttpClient.setWriteTimeout(networkConfig.writeTimeout);



            JSONObject commonHeaders = root.getJSONObject("commonHeaders");
            Set<String> set = commonHeaders.keySet();
            for (String s : set) {
                myHttpClient.addCommonHeader(s, commonHeaders.getString(s));
            }

            MyClientManager.getInstance().addClient(this.key, myHttpClient);
        }catch (Throwable e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
    public Set<String> propertyKeySet(){
        return this.mapProperties.keySet();
    }
    public String getProperty(String key){
        return mapProperties.get(key);
    }

    public NetworkConfig getNetworkConfig() {
        return networkConfig;
    }


    public void addObserver(String type, Observer observer){
        contextObservable.addObserver(type, observer);
    }
    public void notice(String type, Object arg){
        contextObservable.notice(type, arg);
    }
}
