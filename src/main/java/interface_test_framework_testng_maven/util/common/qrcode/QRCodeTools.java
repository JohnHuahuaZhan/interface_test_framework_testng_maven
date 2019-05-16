package interface_test_framework_testng_maven.util.common.qrcode;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
/**
 * 作用：二维码识别（图片）
 * 作者：Tiddler
 * 时间：2018-08-2018/8/31 16：23
 * 类名：QRCodeTools
 **/
public class QRCodeTools {
    /**
     * 解析二维码解析,此方法是解析Base64格式二维码图片
     * baseStr:base64字符串,data:image/png;base64开头的
     */
    public static String deEncodeByBase64(String baseStr) throws Exception {
        byte[] pngBytes = Base64.decodeBase64(baseStr);
        MultiFormatReader formatReader=new MultiFormatReader();
        BufferedImage image=null;
        image = ImageIO.read(new ByteArrayInputStream(pngBytes));
        BinaryBitmap binaryBitmap =new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Hashtable hints=new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        Result result=formatReader.decode(binaryBitmap,hints);
        return result.toString();

    }

    public static void main(String[] args) {
        String code = "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";
            byte[] pngBytes = Base64.decodeBase64(code);
        try {
            System.out.println(deEncodeByBase64(code));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}