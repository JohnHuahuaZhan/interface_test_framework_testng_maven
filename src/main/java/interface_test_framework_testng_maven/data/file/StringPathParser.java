package interface_test_framework_testng_maven.data.file;

import interface_test_framework_testng_maven.util.common.RegexMatcher;

import java.util.Optional;


/**
 * class:path
 * classpath:path
 * pure:path
 */
public class StringPathParser implements IFileInputStreamBuilderParser {
    public static final String pattern = "^(\\w+):([\\w\\/\\\\\\.]+)$";
    protected Optional<String> path;
    protected Optional<Class> cls;

    public StringPathParser( String  path,  Class  cls) {
        this.path = Optional.of(path);
        this.cls = Optional.of(cls);
    }

    public Optional<String> getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = Optional.of(path);
    }

    public Optional<Class> getCls() {
        return cls;
    }

    public void setCls(Class  cls) {
        this.cls = Optional.of(cls);
    }

    @Override
    public FileInputStreamBuilder get() {
        try {
            FileInputStreamBuilder builder = new FileInputStreamBuilder();
            RegexMatcher matcher = new RegexMatcher();


            matcher.setRegex(pattern);
            matcher.setStr(path.get());
            boolean match = matcher.match();
            if(!path.isPresent() || !match)
                throw new RuntimeException("path is illegal:"+path.orElse("null"));
            if(!cls.isPresent())
                throw new RuntimeException("class is empty");
            String[] kv = matcher.getGroup(1);
            switch (kv[0].toLowerCase()) {
                case "class":
                    builder.type(FileInputStreamBuilder.FileSourceType.Class);
                    break;
                case "classpath":
                    builder.type(FileInputStreamBuilder.FileSourceType.ClassPath);
                    break;
                default:
                    builder.type(FileInputStreamBuilder.FileSourceType.Pure);
                    break;
            }

            return builder.cls(cls.get()).path(kv[1]);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
    }
}
