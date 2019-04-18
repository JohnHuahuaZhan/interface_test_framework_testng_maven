package interface_test_framework_testng_maven.guice.module.common;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import interface_test_framework_testng_maven.template.IMarker;
import interface_test_framework_testng_maven.template.freemark.FreemarkerHelper;

public class MarkerModule  extends AbstractModule {
    @Override
    protected void configure() {
        bind(IMarker.class).annotatedWith(Names.named("Freemarker")).toInstance(new FreemarkerHelper());
    }
}
