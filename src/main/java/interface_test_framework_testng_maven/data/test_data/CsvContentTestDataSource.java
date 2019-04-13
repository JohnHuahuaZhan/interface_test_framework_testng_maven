package interface_test_framework_testng_maven.data.test_data;

import interface_test_framework_testng_maven.data.IByteDataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvContentTestDataSource implements ITestDataSource {

    public static final String defaultCharset = "utf-8";
    protected Optional<IByteDataSource> byteDataSource;
    protected Optional<String> charset = Optional.of(defaultCharset);
    public CsvContentTestDataSource() {
    }


    public CsvContentTestDataSource(IByteDataSource content) {
        this.byteDataSource = Optional.of(content);
    }

    public CsvContentTestDataSource(IByteDataSource byteDataSource, String charset) {
        this.byteDataSource = Optional.of(byteDataSource);
        this.charset = Optional.of(charset);
    }

    public void setContent(IByteDataSource content) {
        this.byteDataSource = Optional.of(content);
    }

    public void setCharset(Optional<String> charset) {
        this.charset = charset;
    }

    protected Iterator<Object[]> parse() throws IOException {
        String charset = this.charset.orElse(defaultCharset);

        if (!byteDataSource.isPresent())
            throw new IOException("content is empty");

        try {
            byte[] contentBytes = byteDataSource.get().getData();
            String content = new String(contentBytes, charset);
            CSVParser csvParser = CSVParser.parse(content, CSVFormat.EXCEL);
            return csvParser.getRecords().stream().map(
                    input -> CollectionUtils.collect(input, source -> source).toArray()
            ).collect(Collectors.toList()).iterator();
        } catch (Throwable throwable) {
            throw new IOException(throwable.getMessage(), throwable);
        }
    }
    @Override
    public Iterator<Object[]> getData() throws IOException {
        return parse();
    }
}
