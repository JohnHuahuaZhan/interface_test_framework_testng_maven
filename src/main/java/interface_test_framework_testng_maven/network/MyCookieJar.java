package interface_test_framework_testng_maven.network;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCookieJar implements CookieJar {
    private int count;
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        save(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();
        String host = url.host();
        for (String s : cookieStore.keySet()) {
            if(host.equals(s) || host.contains(s)){
                cookies.addAll(cookieStore.get(s));
            }
        }
        return cookies;

    }

    private void save(HttpUrl url, List<Cookie> cookies){
        for (Cookie cookie : cookies) {
            String domain = cookie.domain();
            if(StringUtils.isEmpty(domain)){
                domain = url.host();
            }
            if(cookieStore.containsKey(domain)){
                cookieStore.get(domain).add(cookie);
            }else {
                List<Cookie> l = new ArrayList<>();
                l.add(cookie);
                cookieStore.put(domain, l);
            }
        }
    }
}
