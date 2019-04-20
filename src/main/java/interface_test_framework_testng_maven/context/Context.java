package interface_test_framework_testng_maven.context;

import interface_test_framework_testng_maven.context.config.NetworkConfig;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.network.MyHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Context {

    public static final String REQUEST_PARSED_TYPE = "REQUEST_PARSED_TYPE";

    private static Context ourInstance = new Context();

    public static Context getInstance() {
        return ourInstance;
    }


    private Context() {

    }
    private ContextObservable contextObservable = new ContextObservable();

    public NetworkConfig networkConfig = new NetworkConfig();

    public Map<String,String> mapProperties = new HashMap<>();

    public void init(){
        loadProperties();
        loadNextWorkConfig();
    }
    public void loadProperties(){
        String classpath = Context.class.getResource("/context").getPath();

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

            IByteDataSource byteDataSource = new StringPathFileByteDataSource("classpath:context/network.json", this.getClass());
            String json = new String(byteDataSource.getData(), "utf-8");

            JSONObject root = new JSONObject(json);

            networkConfig.connectTimeout = Integer.valueOf(root.getInt("connectTimeout"));
            networkConfig.writeTimeout = Integer.valueOf(root.getInt("writeTimeout"));
            networkConfig.readTimeout = Integer.valueOf(root.getInt("readTimeout"));
            MyHttpClient.setConnectTimeout(networkConfig.connectTimeout);
            MyHttpClient.setReadTimeout(networkConfig.readTimeout);
            MyHttpClient.setWriteTimeout(networkConfig.writeTimeout);



            JSONObject commonHeaders = root.getJSONObject("commonHeaders");
            Set<String> set = commonHeaders.keySet();
            for (String s : set) {
                MyHttpClient.addCommonHeader(s, commonHeaders.getString(s));
            }
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
