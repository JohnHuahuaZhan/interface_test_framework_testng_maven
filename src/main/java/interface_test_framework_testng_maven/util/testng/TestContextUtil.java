package interface_test_framework_testng_maven.util.testng;

import org.apache.commons.collections4.CollectionUtils;
import org.testng.ITestContext;
import org.testng.xml.XmlClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestContextUtil {
    public static Map<String, String> getTestParameter(ITestContext context){
            return context.getCurrentXmlTest().getAllParameters();
    }

    public static Map<String, String> getClassParameter(ITestContext context, Class cls){
        //这个classes是一个引用，不要修改它。
        List<XmlClass> classes = context.getCurrentXmlTest().getXmlClasses();
        if(CollectionUtils.sizeIsEmpty(classes))
            return new HashMap<>();


        //这里必须复制一个临时对象。
        List<XmlClass> classesTemp = new ArrayList<>(classes);


        //在下面修改这个临时对象
        CollectionUtils.filter(classesTemp, (XmlClass xmlClass)->{
            return xmlClass.getSupportClass() == cls;
        });
        if(CollectionUtils.sizeIsEmpty(classesTemp))
            return new HashMap<>();

        Map<String, String> testMap = getTestParameter(context);
        Map<String, String> classMap = classesTemp.get(0).getAllParameters();
        testMap.putAll(classMap);
        return testMap;
    }

    public static String getSuiteName(ITestContext context){
        return  context.getSuite().getName();
    }
}
