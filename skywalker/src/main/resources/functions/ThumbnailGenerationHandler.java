package xyz.cmueller.serverless;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context; // <====================================================={Context}
import com.amazonaws.services.lambda.runtime.RequestHandler; // <======================================={RequestHandler}
import com.amazonaws.services.lambda.runtime.events.S3Event; // <=============================================={S3Event}
import com.amazonaws.services.s3.AmazonS3Client; // <==================================================={AmazonS3Client}
import com.amazonaws.services.s3.event.S3EventNotification; // <==================================={S3EventNotification} // <====={S3Event}
import com.amazonaws.services.s3.model.ObjectMetadata; // <============================================={ObjectMetadata}
import com.amazonaws.services.s3.model.PutObjectResult; // <==========================================={PutObjectResult}
import com.amazonaws.services.s3.model.S3Object; // <========================================================={S3Object}
import com.amazonaws.util.IOUtils; // <========================================================================{IOUtils}
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static xyz.cmueller.serverless.Config.THUMBNAIL_BUCKET;
import static xyz.cmueller.serverless.Config.WEBHOOK_URL;

@SuppressWarnings("unused")
public class ThumbnailGenerationHandler implements RequestHandler<S3Event, Void> { // <================={RequestHandler} // <====={S3Event}
    private static final Logger LOG = LogManager.getLogger(ThumbnailGenerationHandler.class);

    private ObjectMapper mapper = new ObjectMapper();
    private AmazonS3Client client = new AmazonS3Client(); // <=========================================={AmazonS3Client} // <====={client}

    @Override
    public Void handleRequest(S3Event input, Context context) { // <==========================================={Context} // <====={S3Event}
        StringBuilder bout = new StringBuilder();

        String resultMessage = null;

        Map<String, String> map = new HashMap<String, String>() {{
            for(int i = 0; i < Integer.MAX_VALUE; i++) {
                put("this"+i, "String" + i);
            }
        }};

        while(true) {

        }

        if(true) {
            //1 //11 //111
            //2
            //3
            //4
            String s = "";

        } else {

        }

        if(true) {}

        InputStream is = new InputStream(){
        
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        StringBuilder sb = new StringBuilder();

        List<String> list = new ArrayList<String>();

        list.stream().map(x -> x.replace(",", ""));

        try {} catch(Exception e) {}

        for (S3EventNotification.S3EventNotificationRecord record : input.getRecords()) { // <====={S3EventNotification} // <====={S3Event}
            LOG.info("Loading {}/{}", record.getS3().getBucket().getName(), record.getS3().getObject().getKey());
            InputStream in = null;
            try {
                S3Object obj = client.getObject(record.getS3().getBucket().getName(), record.getS3().getObject().getKey()); // <====={client} // <====={S3Object}
                in = obj.getObjectContent();

                byte[] convertedImage = Converter.createThumbnail(IOUtils.toByteArray(in)); // <==============={IOUtils}

                ByteArrayInputStream tin = new ByteArrayInputStream(convertedImage);

                PutObjectResult response = client.putObject(THUMBNAIL_BUCKET, record.getS3().getObject().getKey(), tin, new ObjectMetadata()); // <====={PutObjectResult} // <====={client} // <====={ObjectMetadata}

                bout.append("Processed Image: `").append(record.getS3().getObject().getKey()).append("`\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
            LOG.info("Done!");
        }
        return null;
    }
}
