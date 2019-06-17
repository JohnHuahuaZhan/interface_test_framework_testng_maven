package interface_test_framework_testng_maven.util.time;

import org.hamcrest.Matcher;

public class Condition {
    public static  <T> Boolean waitForCondition(Matcher<T> matcher, IProvider<T> actual,long  pollingEvery, long timeout){
       return waitForCondition(matcher, actual, pollingEvery, timeout, ()->{});
    }
    public static  <T> Boolean waitForCondition(Matcher<T> matcher, IProvider<T> actual,long  pollingEvery, long timeout, Runnable runnable){
        FluentWait<T> fluentWait = new FluentWait<>(actual);
        fluentWait.pollingEvery(pollingEvery).withTimeout(timeout);
        return fluentWait.until(ExpectedConditions.match(matcher), runnable);
    }
}
