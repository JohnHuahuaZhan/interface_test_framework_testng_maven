package interface_test_framework_testng_maven.data.file;

import interface_test_framework_testng_maven.data.IInputStreamBuilder;
import interface_test_framework_testng_maven.data.InputStreamByteDataSource;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileInputStreamBuilder implements IInputStreamBuilder {
    public static enum FileSourceType{
        Class,
        ClassPath,
        Pure
    }

    FileSourceType type;
    String path;
    Class cls;

    public FileInputStreamBuilder type(FileSourceType type){
        this.type = type;
        return this;
    }
    public FileInputStreamBuilder path(String path){
        this.path = path;
        return this;
    }
    public FileInputStreamBuilder cls(Class cls){
        this.cls = cls;
        return this;
    }

    public InputStream build(){
        InputStream inputStream = load();
        return inputStream;
    }


    private InputStream load(){
        path = StringUtils.trim(path);
        switch(type){
            case ClassPath:
                if(StringUtils.startsWith(path,"\\")){
                    path = StringUtils.strip(path, "\\");
                }
                if(!StringUtils.startsWith(path,"/")){
                    path = "/"+path;
                }
                return cls.getResourceAsStream(path);
            case Class:
                path = StringUtils.strip(path, "\\/");
                return cls.getResourceAsStream(path);
            case Pure:
                try {
                    InputStream temp = new FileInputStream(path);
                    return temp;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException( e.getMessage(), e);
                }
            default:
                throw new RuntimeException(type + "not supported");
        }
    }
}
