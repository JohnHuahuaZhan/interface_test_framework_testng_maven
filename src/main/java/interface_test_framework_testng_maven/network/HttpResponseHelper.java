package interface_test_framework_testng_maven.network;

import java.util.Optional;

public class HttpResponseHelper {
    public static final String INVALID_TAG_NAME = "HttpResponseHelper_invalid_tag_name";

    public static void updateForm(MyResponse response, byte[] newByte) {
        Optional<MyResponse> op = Optional.of(response);
        if (!op.isPresent() || op.get().getBytes() == null)
            return;

        op.get().setBytes(newByte);
    }

}
