package interface_test_framework_testng_maven.guice.module.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface GuiceByteDataSource {
    String filePath();
    Class cls() default GuiceByteDataSource.class;
    String charset() default "utf-8";
}
