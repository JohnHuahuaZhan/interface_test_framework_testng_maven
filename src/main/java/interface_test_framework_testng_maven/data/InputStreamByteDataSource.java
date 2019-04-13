package interface_test_framework_testng_maven.data;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class InputStreamByteDataSource implements IByteDataSource {
    protected Optional<InputStream> inputStream;

    public InputStreamByteDataSource() {
    }

    public InputStreamByteDataSource(InputStream inputStream) {
        this.inputStream = Optional.of(inputStream);
    }

    public Optional<InputStream> getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream =  Optional.of(inputStream);
    }

    /**
     * 只能获取一次
     * @return
     * @throws Throwable
     */
    @Override
    public byte[] getData() throws Throwable {
        if(!inputStream.isPresent())
            throw new IOException("inputStream is empty");
        byte[] data = IOUtils.toByteArray(inputStream.get());
        IOUtils.closeQuietly(inputStream.get());
        return data;
    }
}
