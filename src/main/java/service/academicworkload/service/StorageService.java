package service.academicworkload.service;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.academicworkload.configuration.MinioConfig;

import java.io.InputStream;

@Service
public class StorageService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Autowired
    public StorageService(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    public InputStream getWorkloadInputStream() {

        try {
            boolean bucket = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.bucket).build());

            if (bucket) {

                // get last item record
                Iterable<Result<Item>> results =
                        minioClient.listObjects(ListObjectsArgs.builder()
                                .bucket(minioConfig.bucket).maxKeys(1).build());

                // record
                Item object = results.iterator().next().get();

                // getting and returning object stream
                return minioClient.getObject(GetObjectArgs.builder()
                        .bucket(minioConfig.bucket).object(object.objectName()).build());
            }

        } catch (Exception e) {
            System.out.println("Exception occurred while downloading: " + e);
            return null;
        }
        return null;

    }

}
