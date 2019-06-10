package interface_test_framework_testng_maven.data.file;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.IInputStreamBuilder;
import interface_test_framework_testng_maven.data.InputStreamByteDataSource;
import interface_test_framework_testng_maven.util.common.RegexMatcher;

import java.util.Optional;


/**
 * 延迟加载，有缓存。可以读取多次，在第一次调用get的时候内容已经定了，之后修改path，cls无效。
 * * class:path
 * * classpath:path
 * * pure:path
 */
public class StringPathFileByteDataSource implements IByteDataSource {

    public static final String pattern = "^(\\w+?):([:\\w\\/\\\\\\.]+)$";
    byte[] content;//缓存

    private Optional<String> path;
    private Optional<Class> cls;
    private IByteDataSource byteDataSource;
    private IInputStreamBuilder inputStreamBuilder;

    public StringPathFileByteDataSource(String path, Class cls) {
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

    public void setCls(Class cls) {
        this.cls = Optional.of(cls);
    }

    private void load() {
        this.inputStreamBuilder = parse();
        byteDataSource = new InputStreamByteDataSource(this.inputStreamBuilder.build());
    }

    @Override
    public byte[] getData() throws Throwable {
        if (null == byteDataSource)
            load();

        if (null == content)
            content = byteDataSource.getData();
        return content;
    }

    protected FileInputStreamBuilder parse() {
        FileInputStreamBuilder builder = new FileInputStreamBuilder();
        RegexMatcher matcher = new RegexMatcher();

        if (!path.isPresent()) {
            throw new RuntimeException("path is null");
        }
        matcher.setRegex(pattern);
        matcher.setStr(path.get());
        boolean match = matcher.match();
        if (!match)
            throw new RuntimeException("path is illegal:" + path.get());
        if (!cls.isPresent())
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
    }
}

