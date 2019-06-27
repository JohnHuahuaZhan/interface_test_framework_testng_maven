package interface_test_framework_testng_maven.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestHelper {
    public static final String INVALID_TAG_NAME = "HttpRequestHelper_invalid_tag_name";
    public static void updateForm(MyRequest request, Map<String, String> map){
        Optional<MyRequest> op = Optional.of(request);
        if(!op.isPresent() || op.get().getMap() == null)
            return;

        op.get().getMap().putAll(map);

    }

    public static void updateForm(MyRequest request, String key, String value){
        Optional<MyRequest> op = Optional.of(request);
        if(!op.isPresent() || op.get().getMap() == null)
            return;

        op.get().getMap().put(key, value);

    }

    public static void deleteForm(MyRequest request, String key){
        Optional<MyRequest> op = Optional.of(request);
        if(!op.isPresent() || op.get().getMap() == null)
            return;

        op.get().getMap().remove(key);
    }

    public static void addHeader(MyRequest request, String key, String value){
        request.addHeader(key,value);
    }


    public static String getTag(MyRequest request){
        return Optional.of(request.getTag()).orElse(INVALID_TAG_NAME);
    }

    public static Map<String,String> getTextParams(MyRequest request){
        Map<String, String> mapMap=request.getMap();
        Map<String,String> multiMapResult=new HashMap<>();
        Map<String, MultiText> multiMap=request.getMultiMap();
        if (!(null == multiMap)) {
            for (Map.Entry<String, MultiText> multiTextEntry : multiMap.entrySet()) {
                multiMapResult.put(multiTextEntry.getKey(), multiTextEntry.getValue().getValue());
            }
        }
        if (!(null == mapMap)) {
            multiMapResult.putAll(mapMap);
        }
        return multiMapResult;
    }
    public static String getTextParam(MyRequest request, String key){
        return getTextParams(request).get(key);
    }
}
