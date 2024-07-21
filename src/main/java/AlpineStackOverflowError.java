import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static software.amazon.awssdk.regions.Region.US_EAST_1;

public class AlpineStackOverflowError {

    private static final Logger logger = LoggerFactory.getLogger(AlpineStackOverflowError.class);

    private static final URI ENDPOINT = URI.create(getenvOrDefault("ENDPOINT", "http://localstack:4566"));
    private static final String AWS_ACCESS_KEY_ID = getenvOrDefault("AWS_ACCESS_KEY_ID", "test");
    private static final String AWS_SECRET_ACCESS_KEY = getenvOrDefault("AWS_SECRET_ACCESS_KEY", "test");
    private static final String TEST_BUCKET_NAME = getenvOrDefault("TEST_BUCKET_NAME", "gh737");

    private static String getenvOrDefault(final String key, final String defaultValue) {
        return System.getenv().getOrDefault(key, defaultValue);
    }

    public static void createAwsResources(final S3AsyncClient s3Client) throws ExecutionException, InterruptedException, TimeoutException {
        logger.info("Creating AWS Resources");
        s3Client
                .createBucket(request -> request.bucket(TEST_BUCKET_NAME))
                .get(1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {

        var credentials = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY));

        try (var s3Client = S3AsyncClient.crtBuilder()
                .endpointOverride(ENDPOINT)
                .forcePathStyle(true)
                .credentialsProvider(credentials)
                .region(US_EAST_1)
                .build()) {

            createAwsResources(s3Client);


        } catch (final Exception e) {
            logger.error("Got exception", e);
        }
    }

}
