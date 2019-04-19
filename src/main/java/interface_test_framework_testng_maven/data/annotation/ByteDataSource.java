package interface_test_framework_testng_maven.data.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface ByteDataSource {
    String filePath();
    Class cls() default ByteDataSource.class;
    String charset() default "utf-8";
}
