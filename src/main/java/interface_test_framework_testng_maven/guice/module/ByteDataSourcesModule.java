package interface_test_framework_testng_maven.guice.module;

import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

public class ByteDataSourcesModule extends InstallCommonModule {

    /**
     * for file source
     */
    String[] filePaths;
    String[] fileNames; //长度不够使用filepath做name
    Class[] classes;//长度不够使用最后一个class做补充.默认本类class
    String[] charsets;//长度不够使用最后一个charset做补充.默认utf-8

    public void setFilePaths(String[] filePaths) {
        this.filePaths = filePaths;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public void setClasses(Class[] classes) {
        this.classes = classes;
    }

    public void setCharsets(String[] charsets) {
        this.charsets = charsets;
    }

    @Override
    protected void config() {


        //StringPathFile
        bind(new TypeLiteral<Map<String, IByteDataSource>>(){}).annotatedWith(Names.named("FilePath")).toInstance(get());
    }
    private Map<String, IByteDataSource> get(){
        if(ArrayUtils.isEmpty(filePaths))
            return new HashMap<>();

        int length = ArrayUtils.getLength(filePaths);

        if(ArrayUtils.isEmpty(classes))
            fileNames = new String[]{};
        if(ArrayUtils.getLength(fileNames) < length){
            String[] tmp = new String[]{};
            tmp = ArrayUtils.addAll(tmp, fileNames);
            tmp = ArrayUtils.addAll(tmp, new String[length - ArrayUtils.getLength(fileNames)]);
            for(int i = ArrayUtils.getLength(fileNames); i < length; i++){
                tmp[i] = filePaths[i];
            }
            fileNames = tmp;
        }

        if(ArrayUtils.isEmpty(classes))
            classes = new Class[]{this.getClass()};
        if(ArrayUtils.getLength(classes) < length){
            Class[] tmp = new Class[]{};
            tmp = ArrayUtils.addAll(tmp, classes);
            tmp = ArrayUtils.addAll(tmp, new Class[length - ArrayUtils.getLength(classes)]);
            for(int i = ArrayUtils.getLength(classes); i < length; i++){
                tmp[i] = classes[ArrayUtils.getLength(classes) - 1];
            }
            classes = tmp;
        }

        if(ArrayUtils.isEmpty(charsets))
            charsets = new String[]{"utf-8"};
        if(ArrayUtils.getLength(charsets) < length){
            String[] tmp = new String[]{};
            tmp = ArrayUtils.addAll(tmp, charsets);
            tmp = ArrayUtils.addAll(tmp, new String[length - ArrayUtils.getLength(charsets)]);
            for(int i = ArrayUtils.getLength(charsets); i < length; i++){
                tmp[i] = charsets[ArrayUtils.getLength(charsets) - 1];
            }
            charsets = tmp;
        }

        Map<String, IByteDataSource> map = new HashMap<>();
        for (int i = 0; i < filePaths.length; i++) {
            map.put(fileNames[i], new StringPathFileByteDataSource(filePaths[i], classes[i]));
        }

        return map;
    }
}
