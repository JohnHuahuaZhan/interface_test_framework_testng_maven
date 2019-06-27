package interface_test_framework_testng_maven.network;


import interface_test_framework_testng_maven.util.common.UrlUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * default get http://localhost:80  utf-8
 */
public class MyRequest {
    public static enum Method{
        POST,
        GET
    }
    public static enum PostMethod{
        URL_ENCODED,
        FORM_DATA,
        RAW,
        BINARY
    }

    Map<String, String> needToDeliverMap = null;
    String tag = "";
    String scheme = "http";
    String host = "localhost";
    String path = "";
    String port = "80";
    Method method = Method.GET;
    PostMethod postMethod = PostMethod.URL_ENCODED;
    Map<String, String> map = new HashMap<>();
    Map<String, String> headerMap = new HashMap<>();
    Map<String, String> commonHeaderMap = new HashMap<>();
    Map<String, MultiFile> fileMap = new HashMap<>();
    Map<String, MultiText> multiMap = new HashMap<>();
    Raw raw;
    String charset = "utf-8";

    public Map<String, Object> extra = new HashMap<>();

    public Object getExtra(String key){
        return extra.get(key);
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        extra = new HashMap<>();
        putExtra(extra);
    }
    public void putExtra(Map<String, Object> extra) {
        this.extra.putAll(extra);
    }
    public void addExtra(String key, Object o){
        extra.put(key, o);
    }
    public String getScheme() {
        return scheme;
    }


    public Map<String, String> getNeedToDeliverMap() {
        return needToDeliverMap;
    }

    public void setNeedToDeliverMap(Map<String, String> needToDeliverMap) {
        this.needToDeliverMap = needToDeliverMap;
    }

    public Map<String, String> getCommonHeaderMap() {
        return commonHeaderMap;
    }

    public void setCommonHeaderMap(Map<String, String> commonHeaderMap) {
        this.commonHeaderMap = commonHeaderMap;
    }
    public void addCommonHeader(String key, String value){
        this.commonHeaderMap.put(key, value);
    }
    public String getCommonHeader(String key){
        return this.commonHeaderMap.get(key);
    }
    public Map<String, String> getHeaderMap() {
        return headerMap;
    }
    public void addHeader(String key, String value){
        this.headerMap.put(key, value);
    }
    public String getHeader(String key){
        return this.headerMap.get(key);
    }
    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public MyRequest setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public MyRequest setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public String getHost() {
        return host;

    }

    public MyRequest setMethod(Method method) {
        this.method = method;
        return this;
    }

    public PostMethod getPostMethod() {
        return postMethod;
    }

    public MyRequest setPostMethod(PostMethod postMethod) {
        this.postMethod = postMethod;
        return this;
    }


    public MyRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPath() {
        return path;
    }

    public MyRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPort() {
        return port;
    }

    public MyRequest setPort(String port) {
        this.port = port;
        return this;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public MyRequest setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }
    public MyRequest setMap(String tokenString) {
        this.map = UrlUtil.getUrlParams(tokenString);
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public MyRequest setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public Map<String, MultiFile> getFileMap() {
        return fileMap;
    }

    public MyRequest setFileMap(Map<String, MultiFile> fileMap) {
        this.fileMap = fileMap;return this;
    }

    public Map<String, MultiText> getMultiMap() {
        return multiMap;
    }

    public MyRequest setMultiMap(Map<String, MultiText> multiMap) {
        this.multiMap = multiMap;
        return this;
    }

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }

    /**
     * map中的内容会自动添加到Form-data请求体中
     * @return
     * @throws IOException
     */
    public Request buildRequest() throws IOException {
        if(null == map)
            map = new HashMap<>();
        String urlWithoutToken =  scheme+"://"+host+":"+port+path;

        String url = "";
        Request.Builder builder = new Request.Builder();
        if(method == Method.GET){
            for (String s : map.keySet()) {
                String value = map.get(s);
                if(StringUtils.isEmpty(value))
                    value = "";
                map.put(s,URLEncoder.encode(map.get(s),charset));
            }
            String token = UrlUtil.getUrlParamsByMap(map);
            url = scheme+"://"+host+":"+port+path+"?"+token;

            builder.get(); //声明我是get请求,如果不写默认就是get
        }
        if(method == Method.POST){
            url = urlWithoutToken;
            RequestBody requestBody = null;
            switch (postMethod){
                case URL_ENCODED:{
                    FormBody.Builder formBody = new FormBody.Builder(Charset.forName(charset));


                    for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                        formBody.add(stringStringEntry.getKey(), stringStringEntry.getValue());
                    }

                    requestBody = formBody.build();
                    break;
                }
                case FORM_DATA:{
                    MultipartBody.Builder  multi = new MultipartBody.Builder();
                    if(null == multiMap)
                        multiMap = new HashMap<>();
                    if(null == fileMap)
                        fileMap = new HashMap<>();
                    multi.setType(MultipartBody.FORM);

                    for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                        multi.addPart(
                                MultipartBody.Part.createFormData(
                                        stringStringEntry.getKey(),
                                        null,
                                        RequestBody.create(
                                                MediaType.parse(String.format("text/plain;charset=%s",charset)),
                                                stringStringEntry.getValue())
                                )

                        );
                    }

                    for (Map.Entry<String, MultiText> stringMultiEntry : multiMap.entrySet()) {
                        multi.addPart(
                                MultipartBody.Part.createFormData(
                                        stringMultiEntry.getKey(),
                                        null,
                                        RequestBody.create(
                                                MediaType.parse(stringMultiEntry.getValue().getMime()),
                                                stringMultiEntry.getValue().getValue())
                                        )
                        );
                    }
                    for (Map.Entry<String, MultiFile> stringMultiFileEntry : fileMap.entrySet()) {
                        multi.addFormDataPart(
                                stringMultiFileEntry.getKey(),
                                stringMultiFileEntry.getValue().getFileName(),
                                RequestBody.create(
                                        MediaType.parse(stringMultiFileEntry.getValue().mime),
                                        stringMultiFileEntry.getValue().getContent()
                                        )
                        );
                    }

                    requestBody = multi.build();
                    break;
                }
                case RAW:{
                    if(null == raw){
                        raw = new Raw("".getBytes(),String.format("text/plain;charset=%s",charset));
                    }
                    requestBody = RequestBody.create(
                            MediaType.parse(raw.getMime()),
                            raw.getValue());
                    break;
                }
                default:
                    throw new RuntimeException("not support post method "+ postMethod +"yet");
            }
            builder.post(requestBody);
        }

        builder.url(url);
        for (Map.Entry<String, String> entry : commonHeaderMap.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "tag='" + tag + '\'' +
                ", scheme='" + scheme + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", port='" + port + '\'' +
                ", method=" + method +
                ", postMethod=" + postMethod +
                '}';
    }
}
