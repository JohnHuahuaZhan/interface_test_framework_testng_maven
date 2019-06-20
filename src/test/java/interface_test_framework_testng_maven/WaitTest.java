package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.util.time.Condition;
import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Matcher的知识请参考hamcrest test
 * http://hamcrest.org/
 */
public class WaitTest{
    @Test(description = "测试wait工具类")
    public void test(){
        String tag = "android";
        boolean b = Condition.waitForCondition(CoreMatchers.hasItem(tag),WaitTest::getList , 500, 6000);
        Assert.assertEquals(b, true);
    }
    @Test(description = "测试wait工具类")
    public void test1(){
        String tag = "android";
        boolean b = Condition.waitForCondition(CoreMatchers.equalTo(tag), ()->"android", 500, 6000);
        Assert.assertEquals(b, true);
    }
    /**
     * 每次调用这个方法获取实际值
     * 每次轮询结束后调用
     * 每次调用这个方法获取实际值
     * 每次轮询结束后调用
     * 每次调用这个方法获取实际值
     * 每次轮询结束后调用
     */
    @Test(description = "测试wait工具类")
    public void testCallback(){
        String tag = "android";
        boolean b = Condition.waitForCondition(CoreMatchers.hasItem(tag), () ->{
            System.out.println("每次调用这个方法获取实际值");
            return  getListError();
        }, 500, 6000, new Runnable() {
            @Override
            public void run() {
                System.out.println("每次轮询结束后调用");
            }
        });
        Assert.assertEquals(b, true);
    }
    public static List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add("android");
        list.add("ios");
        list.add("linux");
        return list;
    }
    public static List<String> getListError(){
        List<String> list = new ArrayList<>();
        //list.add("android");
        list.add("ios");
        list.add("linux");
        return list;
    }
    public static List<String> getListError1(){
        List<String> list = new ArrayList<>();
        //list.add("android");
        list.add("www.ios.net");
        list.add("www.linux.com");

        list = list.stream().filter(t-> t.endsWith(".com")).collect(Collectors.toList());
        System.out.println(list);
        List<Integer> l = Stream.of(1,2,3,4,5,6).collect(Collectors.toList());

        return list;
    }
}
