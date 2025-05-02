package service.academicworkload.repository.model.network.request;

import service.academicworkload.service.storage.state.BucketType;

public record CsvRequest(BucketType storage) {};
