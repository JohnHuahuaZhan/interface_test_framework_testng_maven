package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.context.Context;
import interface_test_framework_testng_maven.context.ContextObservable;
import interface_test_framework_testng_maven.context.Observer;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import interface_test_framework_testng_maven.data.test_data.dataProvider.CsvDataProvider;
import interface_test_framework_testng_maven.data.test_data.dataProvider.DataProviders;
import interface_test_framework_testng_maven.test.Base;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class NoticeTest extends Base {

    private static final String message_type = "Notice_message";


    @BeforeMethod
    public void bm(){
        getContext().addObserver(message_type, new Observer() {
            @Override
            public void update(ContextObservable o, String type, Object arg) {
                System.out.println("收到notice测试消息");
            }
        });
    }

    @Test(description = "notice测试")
    public void testByteDataSource(){
        getContext().notice(message_type, null);
    }
}