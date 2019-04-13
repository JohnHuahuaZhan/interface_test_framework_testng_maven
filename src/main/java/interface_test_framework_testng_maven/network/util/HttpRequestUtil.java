package interface_test_framework_testng_maven.network.util;


import interface_test_framework_testng_maven.network.*;

import java.io.IOException;
import java.util.Map;

public class HttpRequestUtil {
    public static final String POST_URLENCODED = "POST_URLENCODED";

    public static final String POST_FORM_DATA = "POST_FORM_DATA";
    public static final String POST_RAW = "POST_RAW";
    private static MyRequest build(
            String scheme,
            String host,
            String path,
            String port,
            String requestCharset,
            MyRequest.Method method,
            MyRequest.PostMethod postMethod,
            Map<String, String> data,
            Map<String, MultiText> formTextData,
            Map<String, MultiFile> formFileData,
            Raw raw
            ) throws IOException {
        MyRequest myRequest = new MyRequest();
        myRequest.setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .setPort(port)
                .setCharset(requestCharset)
                .setMethod(method)
                .setPostMethod(postMethod)
                .setMap(data)
                .setMultiMap(formTextData)
                .setFileMap(formFileData)
                .setRaw(raw);
        ;

        return myRequest;

    }

    public static byte[] post( String scheme,
                        String host,
                        String path,
                        String port,
                        String requestCharset,
                        String postMethod,
                        Map<String, String> data,
                        Map<String, MultiText> formTextData,
                        Map<String, MultiFile> formFileData,
                               Raw raw
                        ) throws IOException {

        MyRequest.PostMethod pm = null;
        MyRequest request = null;
        switch (postMethod){
            case POST_URLENCODED:
                pm = MyRequest.PostMethod.URL_ENCODED;
                request = build(scheme, host, path, port, requestCharset, MyRequest.Method.POST, pm, data, null, null, null);
                break;
            case POST_FORM_DATA:
                pm = MyRequest.PostMethod.FORM_DATA;
                request = build(scheme, host, path, port, requestCharset, MyRequest.Method.POST, pm, null, formTextData, formFileData, null);
                break;
            case POST_RAW:
                pm = MyRequest.PostMethod.RAW;
                request = build(scheme, host, path, port, requestCharset, MyRequest.Method.POST, pm, null, null, null, raw);
                break;
        }

        MyResponse response = MyHttpClient.request(request);
        return response.bytes();
    }

    /**
     * post urlencoded
     * @param scheme
     * @param host
     * @param path
     * @param port
     * @param requestCharset
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] post( String scheme,
                        String host,
                        String path,
                        String port,
                        String requestCharset,
                        Map<String, String> data
    ) throws IOException {
        return post(scheme, host, path, port, requestCharset, POST_URLENCODED, data, null, null, null);
    }

    /**
     * raw
     * @param scheme
     * @param host
     * @param path
     * @param port
     * @param requestCharset
     * @param raw
     * @return
     * @throws IOException
     */
    public static byte[] post( String scheme,
                               String host,
                               String path,
                               String port,
                               String requestCharset,
                               Raw raw
    ) throws IOException {
        return post(scheme, host, path, port, requestCharset, POST_RAW, null, null, null, raw);
    }
}
