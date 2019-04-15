package interface_test_framework_testng_maven.juice.add;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class AddModule1 extends AbstractModule {
    @Override
    public void configure() {
        Multibinder<String> multiBinder = Multibinder.newSetBinder(binder(), String.class);
        multiBinder.addBinding().toInstance("ERU");
    }
}
