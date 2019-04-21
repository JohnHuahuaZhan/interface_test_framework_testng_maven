package interface_test_framework_testng_maven.test.process;


import interface_test_framework_testng_maven.context.Context;
import interface_test_framework_testng_maven.context.ContextManager;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.network.*;
import interface_test_framework_testng_maven.util.common.RegexMatcher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListJSONHttpRequestPrePostExceptionProcessor extends AbstractJSONHttpPrePostExceptionProcessor implements IDeliverableHttpRequestProcessor {
    public static final String AUTO_PUT_REGEX  = "(\\w+|@)\\.(\\w+)";
    protected String requestJsonString;
    private boolean needAutoPut;
    private Class fileSourceClass;
    private String key;
    protected Map<String, String> deliverMap = new HashMap<>();

    public ListJSONHttpRequestPrePostExceptionProcessor(String key,String requestJsonString, IHttpPrePostExceptionCallback rulePrePostCallback) {
        this(key, requestJsonString, rulePrePostCallback, false);
    }

    public ListJSONHttpRequestPrePostExceptionProcessor(String key,String requestJsonString, IHttpPrePostExceptionCallback rulePrePostCallback, boolean needAutoPut) {
        this(key, requestJsonString, rulePrePostCallback, needAutoPut, ListJSONHttpRequestPrePostExceptionProcessor.class);
    }

    public ListJSONHttpRequestPrePostExceptionProcessor(String key, String requestJsonString, IHttpPrePostExceptionCallback rulePrePostCallback, boolean needAutoPut, Class fileSourceClass) {
        super(rulePrePostCallback);
        this.key = key;
        this.requestJsonString = requestJsonString;
        this.needAutoPut = needAutoPut;
        this.fileSourceClass = fileSourceClass;
    }
    public void addDeliver(String key, String value){
        this.deliverMap.put(key, value);
    }
    public void addDeliver(Map<String, String> deliver){
        this.deliverMap.putAll(deliver);
    }
    public boolean isNeedAutoPut() {
        return needAutoPut;
    }

    public void setNeedAutoPut(boolean needAutoPut) {
        this.needAutoPut = needAutoPut;
    }

    public Class getFileSourceClass() {
        return fileSourceClass;
    }

    public void setFileSourceClass(Class fileSourceClass) {
        this.fileSourceClass = fileSourceClass;
    }

    @Override
    public void start() {
        MyResponse response = null;
        JSONObject jsonObject = new JSONObject(requestJsonString);
        JSONArray array = jsonObject.getJSONArray("requests");
        for (int i = 0; i < array.length(); i++) {
            JSONObject requestJson = array.getJSONObject(i);
            String jsonAfterParse = rulePrePostCallback.preParse(requestJson.toString());
            MyRequest request = super.parse(jsonAfterParse);

            if(needAutoPut){
                replace(request, deliverMap);
            }


            rulePrePostCallback.pre(request, response,deliverMap);
            //发送请求解析完毕消息
            notice(request);
            try {
                response = MyClientManager.getInstance().getClient(this.key).request(request);
                rulePrePostCallback.post(request,response,deliverMap);
            } catch (IOException e) {
                rulePrePostCallback.exception(request,e,deliverMap);
            }
        }
    }

    private void replace(MyRequest request, Map<String, String> deliverMap){
        RegexMatcher matcher = new RegexMatcher();
        matcher.setRegex(AUTO_PUT_REGEX);
        String tag = HttpRequestHelper.getTag(request);



        for (Map.Entry<String, String> entry : deliverMap.entrySet()) {
            matcher.setStr(entry.getKey());
            if(matcher.match()){
                String[] values= matcher.getGroup(1);
                String key = values[1];
                if(values[0].equals("@") || values[0].equals(tag)){
                    HttpRequestHelper.updateForm(request, key, entry.getValue());
                }
            }
        }
    }

    @Override
    protected byte[] content(String pattern) {
        IByteDataSource dataSource = new StringPathFileByteDataSource(pattern, fileSourceClass);
        try {
            return dataSource.getData();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }

    public void notice(MyRequest request){
        ContextManager.getInstance().getContext(this.key).notice(Context.REQUEST_PARSED_TYPE, request);
    }
}
