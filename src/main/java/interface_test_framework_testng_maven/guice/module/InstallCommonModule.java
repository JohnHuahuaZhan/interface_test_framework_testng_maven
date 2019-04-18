package interface_test_framework_testng_maven.guice.module;

import com.google.inject.AbstractModule;
import interface_test_framework_testng_maven.guice.module.common.MarkerModule;

public abstract class InstallCommonModule extends AbstractModule {

    protected abstract void config();

    @Override
    protected void configure() {
        install(new MarkerModule());
        config();
    }
}
