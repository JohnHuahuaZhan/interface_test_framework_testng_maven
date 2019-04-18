package interface_test_framework_testng_maven.context.config;

import java.util.Map;

public class NetworkConfig {
    public int connectTimeout=10;
    public int writeTimeout=8;
    public int readTimeout=8;

    public Map<String, String> commonHeader;
    @Override
    public String toString() {
        return "NetworkConfig{" +
                "connectTimeout=" + connectTimeout +
                ", writeTimeout=" + writeTimeout +
                ", readTimeout=" + readTimeout +
                '}';
    }
}
