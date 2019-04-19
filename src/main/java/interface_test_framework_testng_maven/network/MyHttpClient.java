package interface_test_framework_testng_maven.network;


import interface_test_framework_testng_maven.util.common.network.RxUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyHttpClient {
    private static long  connectTimeout = 6;
    private static long  writeTimeout = 6;
    private static long  readTimeout = 6;

    private static Map<String, String> commonHeader = new HashMap<>();
    private static ThreadLocal<OkHttpClient> threadLocal = new ThreadLocal<OkHttpClient>(){
        @Override
        protected OkHttpClient initialValue() {


            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(RxUtils.createSSLSocketFactory())
                        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                        .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                        .readTimeout(readTimeout, TimeUnit.SECONDS)
                        .cookieJar(new MyCookieJar())
                        .hostnameVerifier(new RxUtils.TrustAllHostnameVerifier())
                        .build();

             return client;
            }catch (Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    };

    private MyHttpClient() {
    }

    public static Map<String, String> getCommonHeader() {
        return commonHeader;
    }

    public static void setCommonHeader(Map<String, String> commonHeader) {
        MyHttpClient.commonHeader = commonHeader;
    }

    public static void addCommonHeader(String name, String value){
        MyHttpClient.commonHeader.put(name, value);
    }
    public static long getConnectTimeout() {
        return connectTimeout;
    }

    public static void setConnectTimeout(long connectTimeout) {
        MyHttpClient.connectTimeout = connectTimeout;
    }

    public static long getWriteTimeout() {
        return writeTimeout;
    }

    public static void setWriteTimeout(long writeTimeout) {
        MyHttpClient.writeTimeout = writeTimeout;
    }

    public static long getReadTimeout() {
        return readTimeout;
    }

    public static void setReadTimeout(long readTimeout) {
        MyHttpClient.readTimeout = readTimeout;
    }

    public static OkHttpClient getClient(){
        return threadLocal.get();
    }

    /**
     * 你需要在外调用request.body.bytes() 或者request.body.string()或者request.body.close()来回收连接
     * @param request
     * @return
     * @throws IOException
     */
    public static MyResponse request(MyRequest request) throws IOException {
        request.setHeaderMap(commonHeader);
        Call call = threadLocal.get().newCall(request.buildRequest());
        Response response = call.execute();
        return new MyResponse(response);
    }
}
