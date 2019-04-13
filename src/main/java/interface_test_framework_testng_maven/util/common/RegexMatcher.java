package interface_test_framework_testng_maven.util.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {
    private String str;
    private String regex;
    private Pattern pattern;

    public RegexMatcher(String str, String regex) {
        this.str = str;
        this.regex = regex;
        this.pattern = Pattern.compile(this.regex);
    }

    public RegexMatcher() {
    }

    public String getStr() {
        return this.str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(this.regex);
    }

    public boolean match() {
        Matcher m = this.pattern.matcher(this.str);
        return m.find();
    }

    public String getGroup(int findCount, int groupIndex) {
        Matcher m = this.pattern.matcher(this.str);

        for(int i = 1; i <= findCount; ++i) {
            if (!m.find()) {
                return null;
            }
        }

        if (groupIndex > m.groupCount()) {
            return null;
        } else {
            return m.group(groupIndex);
        }
    }

    public List<List<String>> getGroup() {
        List<List<String>> result = new ArrayList();
        Matcher m = this.pattern.matcher(this.str);

        while(m.find()) {
            int count = m.groupCount();
            List<String> temp = new ArrayList();

            for(int i = 1; count-- != 0; ++i) {
                temp.add(m.group(i));
            }

            result.add(temp);
        }

        return result;
    }

    public String[] getGroup(int findCount) {
        Matcher m = this.pattern.matcher(this.str);

        for(int i = 1; i <= findCount; ++i) {
            if (!m.find()) {
                return null;
            }
        }

        String[] values = new String[m.groupCount()];

        for(int i = 0; i < values.length; ++i) {
            values[i] = m.group(i + 1);
        }

        return values;
    }


    public void replace(StringBuffer stringBuffer,IMatcherHandler iMatcherHandler){
        Matcher m = this.pattern.matcher(this.str);
        while(m.find()){
            String replacement = iMatcherHandler.match(m);
            m.appendReplacement(stringBuffer,replacement);
        }
        m.appendTail(stringBuffer);
    }

    /**
     *
     * @param stringBuffer
     * @param map
     */
    public void replace(StringBuffer stringBuffer, Map<String,String> map){
        this.replace(stringBuffer, new IMatcherHandler() {
            @Override
            public String match(Matcher matcher) {
                int count = matcher.groupCount();
                StringBuffer buffer = new StringBuffer();
                for (int i = 1; i <= count; i++) {
                    buffer.append(map.get(matcher.group(i)));
                }
                return buffer.toString();
            }
        });
    }




    public static void main(String[] args) {

    }

}