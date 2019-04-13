package interface_test_framework_testng_maven.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceFactory {
    private DataSourceFactory(){ }

    private static DataSourceFactory instance;

    public static DataSourceFactory getInstance(){
        if(null == instance)
            instance = new DataSourceFactory();
        return instance;
    }



    private Map<String, DataSource> sourcesMap = new ConcurrentHashMap<>();


    public DataSource getDataSource(String name){
        DataSource source = null;
        if(!sourcesMap.containsKey(name)){
            source = new ComboPooledDataSource(name);
            sourcesMap.put(name,source);
        }
        return sourcesMap.get(name);
    }

}
