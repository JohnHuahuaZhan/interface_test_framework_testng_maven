package interface_test_framework_testng_maven.juice.add;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;

public class AddModule extends AbstractModule {
    @Override
    public void configure() {
        //bind(String.class).toInstance("hi");
        //bind(new TypeLiteral<List<String>>(){}).toInstance(Arrays.asList(new String[]{"a", "b"}));
        Multibinder<String> multiBinder = Multibinder.newSetBinder(binder(), String.class);
        multiBinder.addBinding().toInstance("CNY");
    }

    @Provides
    @Named("id")
    public Long id(Add a){
        System.out.println(a);
        return Long.valueOf(a.hashCode());
    }
}
