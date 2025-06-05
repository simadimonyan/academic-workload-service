package service.academicworkload.service.storage;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.academicworkload.service.configuration.CsvServiceConfiguration;
import service.academicworkload.service.csv.model.CsvDepartment;
import service.academicworkload.service.csv.model.CsvGroup;
import service.academicworkload.service.csv.model.CsvWorkload;
import service.academicworkload.service.storage.state.BucketType;

import java.io.InputStream;
import java.util.ArrayList;

@Service
public class StorageService {

    private final MinioClient minioClient;
    private final CsvServiceConfiguration configuration;


    @Autowired
    public StorageService(MinioClient minioClient, CsvServiceConfiguration configuration) {
        this.minioClient = minioClient;
        this.configuration = configuration;
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

    public ArrayList<CsvWorkload> parseWorkloadFile() {
        InputStream file = getWorkload();
        if (file != null) {
            return (ArrayList<CsvWorkload>) configuration.workloadParser().parse(file);
        }
        return new ArrayList<>();
    }

    public ArrayList<CsvGroup> parseGroupFile() {
        InputStream file = getGroups();
        if (file != null) {
            return (ArrayList<CsvGroup>) configuration.groupParser().parse(file);
        }
        return new ArrayList<>();
    }

    public ArrayList<CsvDepartment> parseDepartmentFile() {
        InputStream file = getDepartments();
        if (file != null) {
            return (ArrayList<CsvDepartment>) configuration.departmentParser().parse(file);
        }
        return new ArrayList<>();
    }

    private InputStream getWorkload() {
        return getInputStreamByBucket(BucketType.WORKLOAD.getBucketName());
    }

    private InputStream getGroups() {
        return getInputStreamByBucket(BucketType.GROUP.getBucketName());
    }

    private InputStream getDepartments() {
        return getInputStreamByBucket(BucketType.DEPARTMENT.getBucketName());
    }


}
