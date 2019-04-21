package interface_test_framework_testng_maven.init.observer;

import interface_test_framework_testng_maven.context.ContextObservable;
import interface_test_framework_testng_maven.context.Observer;
import interface_test_framework_testng_maven.network.MyRequest;

public class RequestSignObserver implements Observer {
    @Override
    public void update(ContextObservable o, String type, Object arg) {
        if(null != arg && arg.getClass() == MyRequest.class){
            MyRequest request = (MyRequest) arg;
            System.out.println("收到请求解析完毕消息，开始签名："+Thread.currentThread().getName());
            System.out.println(request);
        }
    }
}
