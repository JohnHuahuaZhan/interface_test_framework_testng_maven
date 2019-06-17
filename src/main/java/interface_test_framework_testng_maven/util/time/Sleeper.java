/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;

public interface Sleeper {

    Sleeper SYSTEM_SLEEPER = duration -> Thread.sleep(duration);

    /**
     * Sleeps for the specified duration of time.
     * @param duration 毫秒
     * @throws InterruptedException
     */
    void sleep(long duration) throws InterruptedException;
}