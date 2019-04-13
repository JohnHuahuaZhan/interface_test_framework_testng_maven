package interface_test_framework_testng_maven.util.common;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StringUtil {


    /**
     * 非空字符串将被trim，null或者空字符串，将变成空字符串。
     * @param str
     * @return String
     */
    public static  String pureString(String str){
        if(null == str){
            str = "";
        }
        str = str.trim();
        return str;
    }
    /**
     * 剔除字符串中所有非数字字符。包括小数点
     * @param str
     * @return
     */
    public static  String onlyNumber(String str){
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            try{
                Short.valueOf(new String(new char[]{c}));
                charList.add(c);
            }catch (NumberFormatException e){
                //do nothing
            }
        }
        char[] charArray = new char[charList.size()];
        for (int i = 0; i < charList.size(); i++) {
            charArray[i] = charList.get(i);
        }
        return new String(charArray);
    }

    /**
     * 过滤掉任何空的，或者trim后为空的字符串
     * @param source
     * @param split
     * @return List<String>
     */
    public static List<String> pureSplit(String source, String  split){
        List<String> list = new ArrayList<>();
        String[] array = source.split(split);
        for (int i = 0; i < array.length; i++) {
            String value = pureString(array[i]);
            if(!value.isEmpty())
                list.add(value);
        }
        return list;
    }
    public static List<String> split(String source, String  split){
        List<String> list = new ArrayList<>();
        String[] array = source.split(split);
        for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
        }
        return list;
    }


    public static String join(String[] strArray, List<String> list, boolean appendEnd) {
       StringBuffer buffer = new StringBuffer();
       String join = "";
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i));
            if(i < strArray.length)
                join = strArray[i];
            if(i >= strArray.length){
                join = "";
            }
            if(i >= list.size() - 1){
                if(!appendEnd){
                    join = "";
                }
            }


            buffer.append(join);
        }
        return buffer.toString();
    }


    /**
     * 先把数字转为36进制，然后将其中的数字转为大写字母
     * @param str
     * @return
     */
    public static String _10_to_36(String str){
        BigInteger bi = new BigInteger(str);
        String result = numToLetter(bi.toString(36),true);
        return result;
    }

    // 将数字转换成字母
    public static String numToLetter(String input,boolean upCase) {
        List<String> characterList = new ArrayList<>();
        for(Character i='a';i<='j';i++){
            characterList.add(i.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        String s = input;
        for (int i = 0; i < s.length(); i++) {
            String sub = s.substring(i,i+1);
            try{
                int index = Integer.valueOf(sub);
                sub = characterList.get(index);
                if(upCase){
                    sub = sub.toUpperCase();
                }
            }catch (NumberFormatException e){

            }

            stringBuffer.append(sub);
        }
        return stringBuffer.toString();
    }
    public static <T> String[] stringArray(T[] array, Function<T,String> function) {
       String[] a = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            a[i] = function.apply(array[i]);
        }
        return a;
    }

    /**
     *string will be trimed .if str is null  return true;
     * @param str
     * @return  boolean
     */
    public static  boolean isEmptyIncludeNull(String str){
        str = pureString(str);
        return str.isEmpty();
    }

    public static boolean isInteger(String str){
        try{
            Integer.valueOf(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static long toLong(String source){
        BigDecimal bigDecimal = new BigDecimal(source);
        return bigDecimal.longValue();
    }
    public static double toDouble(String source){
        BigDecimal bigDecimal = new BigDecimal(source);
        return bigDecimal.doubleValue();
    }

    /**
     * 获取时间戳，并且转为36进制
     */
    public static String setRandomStrings() {
        long timeSample = System.currentTimeMillis();
        return _10_to_36(timeSample + "");
    }

}
