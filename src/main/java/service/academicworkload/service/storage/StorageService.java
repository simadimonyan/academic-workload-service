package service.academicworkload.service.storage;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.academicworkload.service.configuration.StorageConfiguration;

import java.io.InputStream;

@Service
public class StorageService {

    private final MinioClient minioClient;
    private final StorageConfiguration storageConfiguration;

    @Autowired
    public StorageService(MinioClient minioClient, StorageConfiguration storageConfiguration) {
        this.minioClient = minioClient;
        this.storageConfiguration = storageConfiguration;
    }

    public InputStream getWorkloadInputStream() {

        try {
            boolean bucket = minioClient.bucketExists(BucketExistsArgs.builder().bucket(storageConfiguration.bucket).build());

            if (bucket) {

                // get last item record
                Iterable<Result<Item>> results =
                        minioClient.listObjects(ListObjectsArgs.builder()
                                .bucket(storageConfiguration.bucket).maxKeys(1).build());

                // record
                Item object = results.iterator().next().get();

                // getting and returning object stream
                return minioClient.getObject(GetObjectArgs.builder()
                        .bucket(storageConfiguration.bucket).object(object.objectName()).build());
            }

        } catch (Exception e) {
            System.out.println("Exception occurred while downloading: " + e);
            return null;
        }
        return null;

    }

}
