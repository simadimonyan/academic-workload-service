package service.academicworkload.service.storage;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.academicworkload.repository.model.network.reponse.CsvResponse;
import service.academicworkload.service.csv.CsvParseFactory;
import service.academicworkload.service.csv.CsvParseService;
import service.academicworkload.service.csv.model.parent.CsvModel;
import service.academicworkload.service.csv.state.CsvContentType;
import service.academicworkload.service.storage.state.BucketType;

import java.io.InputStream;
import java.util.ArrayList;

@Service
public class StorageService {

    private final MinioClient minioClient;

    @Autowired
    public StorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    private void createBucketIfNotExists(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            System.out.println("Error creating bucket: " + e);
        }
    }

    private InputStream getInputStreamByBucket(String bucketName) {
        try {
            createBucketIfNotExists(bucketName);

            Iterable<Result<Item>> results =
                    minioClient.listObjects(ListObjectsArgs.builder()
                            .bucket(bucketName).maxKeys(1).build());

            Item object = results.iterator().next().get();

            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName).object(object.objectName()).build());
        } catch (Exception e) {
            System.out.println("Exception occurred while downloading: " + e);
            return null;
        }
    }

    public <T extends CsvModel> CsvResponse parseFile(InputStream file, CsvContentType<T> type) {
        if (file != null) {
            CsvParseService<T> service = CsvParseFactory.create(type);
            ArrayList<T> list = (ArrayList<T>) service.parse(file);

            if (!list.isEmpty()) {
                return new CsvResponse("200", list.get(1).toString());
            } else {
                return new CsvResponse("404", "File is empty");
            }
        }
        return new CsvResponse("404", "Loaded file does not exit!");
    }

    public InputStream getWorkload() {
        return getInputStreamByBucket(BucketType.WORKLOAD.getBucketName());
    }

    public InputStream getGroups() {
        return getInputStreamByBucket(BucketType.GROUP.getBucketName());
    }

    public InputStream getDepartments() {
        return getInputStreamByBucket(BucketType.DEPARTMENT.getBucketName());
    }


}
