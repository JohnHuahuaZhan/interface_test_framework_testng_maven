package interface_test_framework_testng_maven.juice.add;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class AddModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Add.class).annotatedWith(Names.named("basic")).toProvider(BasicProvider.class);
        binder.bind(Add.class).annotatedWith(Names.named("other")).toProvider(OtherProvider.class);
    }
}
