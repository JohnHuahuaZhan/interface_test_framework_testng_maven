/**
 * @author 菜花
 * @date 20190524
 */
package interface_test_framework_testng_maven.util.time;

import org.hamcrest.Matcher;


public class ExpectedConditions {
    public  static <T> Function<T, Boolean> match(Matcher<T> matcher){
       return new Function<T, Boolean>() {
           @Override
           public Boolean apply(T t) {
               return matcher.matches(t);
           }
       };
    }
}
