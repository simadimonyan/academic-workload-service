package service.academicworkload.service.storage.state;

import lombok.Getter;

@Getter
public enum BucketType {

    WORKLOAD("academic-workload"),
    DEPARTMENT("departments"),
    GROUP("groups"),
    AUDITORY("auditories");

    private final String bucketName;

    BucketType(String bucketName) {
        this.bucketName = bucketName;
    }

}
