/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;


import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An implementation of the {@link Wait} interface that may have its timeout and polling interval
 * configured on the fly.
 *
 * <p>
 * Each FluentWait instance defines the maximum amount of time to wait for a condition, as well as
 * the frequency with which to check the condition. Furthermore, the user may configure the wait to
 * ignore specific types of exceptions whilst waiting,  when searching for an
 * element on the page.
 *
 * <p>
 * Sample usage: <pre>
 *   // Waiting 30 seconds for an element to be present on the page, checking
 *   // for its presence once every 5 seconds.
 *   Wait&lt;View&gt; wait = new FluentWait&lt;View&gt;(view)
 *       .withTimeout(30, SECONDS)
 *       .pollingEvery(5, SECONDS)
 *       .ignoring(NoMatchingViewException.class);
 *
 *   ViewInteraction foo = wait.until(new Function&lt;View, ViewInteraction&gt;() {
 *     public ViewInteraction apply(view view) {
 *       return onView(clickable()).;
 *     }
 *   });
 * </pre>
 *
 * <p>
 * <em>This class makes no thread safety guarantees.</em>
 *
 * @param <T> The input type for each condition used with this instance.
 */
public class FluentWait<T> implements Wait<T> {

    private final IProvider<T> input;
    private final Clock clock;
    private final Sleeper sleeper;

    /**
     * 超时时间，单位毫秒
     */
    private long timeout;
    /**
     * 轮询间隔，单位毫秒
     */
    private  long interval;

    private List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();

    /**
     * @param input The input value to pass to the evaluated conditions.
     */
    public FluentWait(IProvider<T> input) {
        this(input, new SystemClock(), Sleeper.SYSTEM_SLEEPER);
    }

    /**
     * @param input The input value to pass to the evaluated conditions.可以为空
     * @param clock The clock to use when measuring the timeout.
     * @param sleeper Used to put the thread to sleep between evaluation loops.
     */
    public FluentWait(IProvider<T> input, Clock clock, Sleeper sleeper) {
        this.input = input;
        this.clock = checkNotNull(clock);
        this.sleeper = checkNotNull(sleeper);
    }



    /**
     * Sets how long to wait for the evaluated condition to be true. The default timeout is
     *
     * @param timeout 毫秒
     * @return A self reference.
     */
    public FluentWait<T> withTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }




    /**
     * Sets how often the condition should be evaluated.
     *
     * <p>
     * In reality, the interval may be greater as the cost of actually evaluating a condition function
     *
     * @param interval 毫秒
     * @return A self reference.
     */
    public FluentWait<T> pollingEvery(long interval) {
        this.interval = interval;
        return this;
    }

    /**
     * Configures this instance to ignore specific types of exceptions while waiting for a condition.
     * Any exceptions not whitelisted will be allowed to propagate, terminating the wait.
     *
     * @param types The types of exceptions to ignore.
     * @param <K> an Exception that extends Throwable
     * @return A self reference.
     */
    public <K extends Throwable> FluentWait<T> ignoreAll(Collection<Class<? extends K>> types) {
        ignoredExceptions.addAll(types);
        return this;
    }

    /**
     * @see #ignoreAll(Collection)
     * @param exceptionType exception to ignore
     * @return a self reference
     */
    public FluentWait<T> ignoring(Class<? extends Throwable> exceptionType) {
        return this.ignoreAll(ImmutableList.<Class<? extends Throwable>>of(exceptionType));
    }

    /**
     * @see #ignoreAll(Collection)
     * @param firstType exception to ignore
     * @param secondType another exception to ignore
     * @return a self reference
     */
    public FluentWait<T> ignoring(Class<? extends Throwable> firstType,
                                  Class<? extends Throwable> secondType) {

        return this.ignoreAll(ImmutableList.of(firstType, secondType));
    }

    /**
     * Repeatedly applies this instance's input value to the given function until one of the following
     * occurs:
     * <ol>
     * <li>the function returns neither null nor false</li>
     * <li>the function throws an unignored exception</li>
     * <li>the timeout expires</li>
     * <li>the current thread is interrupted</li>
     * </ol>
     *
     * @param <V> The function's expected return type.
     * @return The function's return value if the function returned something different
     *         from null or false before the timeout expired.
     */
    @Override
    public <V> V until(Function<? super T, V> isTrue, Runnable runnable) {
        long end = clock.laterBy(timeout);
        Throwable lastException;
        while (true) {
            V value = null;
            try {
                value = isTrue.apply(input.get());
                if (value != null && (Boolean.class != value.getClass() || Boolean.TRUE.equals(value))) {
                    return value;
                }

                // Clear the last exception; if another retry or timeout exception would
                // be caused by a false or null value, the last exception is not the
                // cause of the timeout.
                lastException = null;
            } catch (Throwable e) {
                lastException = propagateIfNotIgnored(e);
            }

            // Check the timeout after evaluating the function to ensure conditions
            // with a zero timeout can succeed.
            if (!clock.isNowBefore(end)) {
                return value;
            }

            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TestException(e);
            }
            if(runnable != null)
                runnable.run();
        }
    }

    private Throwable propagateIfNotIgnored(Throwable e) {
        for (Class<? extends Throwable> ignoredException : ignoredExceptions) {
            if (ignoredException.isInstance(e)) {
                return e;
            }
        }
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
    }
}