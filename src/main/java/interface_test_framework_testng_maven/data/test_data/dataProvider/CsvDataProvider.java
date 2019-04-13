package interface_test_framework_testng_maven.data.test_data.dataProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CsvDataProvider {
    String path() default "";
    Class cls() default CsvDataProvider.class;
    String charset() default "utf-8";
}
