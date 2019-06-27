package interface_test_framework_testng_maven.test;

import interface_test_framework_testng_maven.annotation.IgnoreNamedParam;
import interface_test_framework_testng_maven.annotation.NamedParam;
import interface_test_framework_testng_maven.annotation.Scenario;
import interface_test_framework_testng_maven.util.testng.TestContextUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.IClass;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class CommonBase extends Base implements ITest {

    private Map<String, String> allParameters;
    public Map<String, String> getAllParameters() {
        return allParameters;
    }
    @BeforeClass
    protected void commonBase_before_class_init_parameter(ITestContext context){
        allParameters = TestContextUtil.getClassParameter(context, this.getClass());
    }


    //每次执行test的时候更新方法名
    private String methodName;
    public String getMethodName() {
        return methodName;
    }
    @BeforeMethod
    protected void commonBase_before_method_init_methodName(Method m){
        methodName = m.getName();
    }

    //每次执行test的时候更新参数列表
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    @BeforeMethod
    public void commonBase_before_method_init_param(Method m, Object[] objects){
        params = new HashMap<>();
        Parameter[] parameters = m.getParameters();

        if(ArrayUtils.isEmpty(parameters)){
            return;
        }else {
            for (int i = 0; i < m.getParameters().length; i++) {
                boolean ignore = false;
                String name = parameters[i].getName();
                Annotation[] annotations = parameters[i].getAnnotations();
                if(!ArrayUtils.isEmpty(annotations)){
                    for (Annotation annotation : annotations) {
                        if(annotation.annotationType() == IgnoreNamedParam.class){
                            ignore = true;
                            break;
                        }
                        if(annotation.annotationType() == NamedParam.class){
                            NamedParam named = (NamedParam) annotation;
                            name = named.value();
                        }

                    }
                }

                if(!ignore)
                    params.put(name, objects[i].toString());
            }
        }

        allParameters.putAll(params);
    }


    //每次执行class的时候更新class名称
    private String testName;
    @Override
    public String getTestName() {
        Scenario withTestName = this.getClass().getAnnotation(Scenario.class);
        if( null == withTestName){
            testName = this.getClass().getName();
        }else {
            testName = withTestName.value();
        }
        return testName;
    }
}
