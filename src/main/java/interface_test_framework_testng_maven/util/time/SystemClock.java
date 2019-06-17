/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;

public class SystemClock implements Clock {

    public long laterBy(long durationInMillis) {
        return System.currentTimeMillis() + durationInMillis;
    }

    public boolean isNowBefore(long endInMillis) {
        return System.currentTimeMillis() < endInMillis;
    }

    public long now() {
        return System.currentTimeMillis();
    }
}
