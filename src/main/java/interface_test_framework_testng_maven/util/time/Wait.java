/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;



public interface Wait<F> {
    <T> T until(Function<? super F, T> function, Runnable runnable);
}
