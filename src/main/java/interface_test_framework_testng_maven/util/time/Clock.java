/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;

/**
 * A simple encapsulation to allowing timing
 */
public interface Clock {

    /**
     * @return The current time in milliseconds since epoch time.
     */
    long now();

    /**
     * Computes a point of time in the future.
     *
     * @param durationInMillis The point in the future, in milliseconds relative to the {@link #now()
     *        current time}.
     * @return A timestamp representing a point in the future.
     */
    long laterBy(long durationInMillis);

    /**
     * Tests if a point in time occurs before the {@link #now() current time}.
     *
     * @param endInMillis The timestamp to check.
     * @return Whether the given timestamp represents a point in time before the current time.
     */
    boolean isNowBefore(long endInMillis);

}
