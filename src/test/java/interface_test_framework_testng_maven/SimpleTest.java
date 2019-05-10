package interface_test_framework_testng_maven;

import interface_test_framework_testng_maven.data.IByteDataSource;
import interface_test_framework_testng_maven.data.file.StringPathFileByteDataSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class SimpleTest {
    public static String unzip(byte[] content) throws Throwable {
        InputStream inflInstream = new InflaterInputStream(new ByteArrayInputStream(ArrayUtils.subarray(content, 10, content.length - 8)),
                new Inflater(true));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte bytes[] = new byte[1024];
        while (true) {
            int length = inflInstream.read(bytes, 0, 1024);
            if (length == -1) break;

            byteArrayOutputStream.write(bytes, 0, length);
        }
        inflInstream.close();
        String str = new String(byteArrayOutputStream.toByteArray());
        return str;
    }

    public static byte[] zip(byte[] bytes) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(bytes);
        gzipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    // 将数字转换成字母
    public static String numToLetter(String input,boolean upCase) {
        List<String> characterList = new ArrayList<>();
        for(Character i='a';i<='j';i++){
            characterList.add(i.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        String s = input;
        for (int i = 0; i < s.length(); i++) {
            String sub = s.substring(i,i+1);
            try{
                int index = Integer.valueOf(sub);
                sub = characterList.get(index);
                if(upCase){
                    sub = sub.toUpperCase();
                }
            }catch (NumberFormatException e){

            }

            stringBuffer.append(sub);
        }
        return stringBuffer.toString();
    }
    @Test
    void test() throws Throwable {
        IByteDataSource byteDataSource = new StringPathFileByteDataSource("classpath:test/base/resigned_kktribe.ipa", this.getClass());
        byte[] bytes = byteDataSource.getData();

        bytes = Base64.encodeBase64(bytes);
        bytes = zip(bytes);
        FileUtils.writeByteArrayToFile(new File("d:/base64zip"), bytes);
    }
}
