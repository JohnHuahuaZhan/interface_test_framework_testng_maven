package interface_test_framework_testng_maven.test;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.annotation.ByteDataSources;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public abstract class MultiClassLoadFileBase extends CommonBase {
    private Map<String, String> content;

    @BeforeClass
    protected  void beforeClass() throws Throwable {
        ByteDataSources byteDataSource = this.getClass().getAnnotation(ByteDataSources.class);
        if(byteDataSource == null){
            throw new RuntimeException("当你的类继承MultiClassLoadFileBase，类必须用ByteDataSources注释");
        }
        content = get(byteDataSource);
    }
    private Map<String, String> get(ByteDataSources byteDataSource) throws Throwable {
        String[] filePaths = byteDataSource.filePaths();
        String[] fileNames = byteDataSource.fileNames();
        Class[] classes = byteDataSource.classes();
        String[] charsets = byteDataSource.charsets();
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

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < filePaths.length; i++) {
            IByteDataSource dataSource = new StringPathFileByteDataSource(filePaths[i], classes[i]);
            String value = new String(dataSource.getData(), charsets[i]);

            map.put(fileNames[i], value);
        }

        return map;
    }
    public Map<String, String> getFileContent(){
        return content;
    }
}
