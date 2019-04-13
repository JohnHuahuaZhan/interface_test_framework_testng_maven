package interface_test_framework_testng_maven.template.freemark;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import interface_test_framework_testng_maven.template.IMarker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


/**
 * 线程安全
 */
public class FreemarkerHelper implements IMarker {
    public  String mark(String source, Map<String, Object> map) throws IOException, TemplateException {
        Configuration cfg = MyFreemarkerConfiguration.getInstance().getConfiguration();
        Template template = new Template("mark", source, cfg);
        Writer stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.toString();
    }
}
