package interface_test_framework_testng_maven.network;

import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class MyResponse {
    private Response response;
    private byte[] bytes;

    protected MyResponse(Response response) {
        this.response = response;
    }

    public byte[] bytes() throws IOException {
        if(bytes == null){
            bytes = response.body().bytes(); //okhttp3的bytes方法内部已经关闭了body。所以调用者不需要再关闭。
        }
        return bytes ;
    }
    public String string(String resultCharset) throws IOException {
        return new String(bytes(), resultCharset);
    }
    public void close(){
        response.body().close();
    }
    public String header(String name){
        return header(name, null);
    }
    public String header(String name, String defaultValue){
        return response.header(name, defaultValue);
    }
    public List<String> headers(String name){
        return headers(name);
    }
    public int code(){
        return response.code();
    }
}
