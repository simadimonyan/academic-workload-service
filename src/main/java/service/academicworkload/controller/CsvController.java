package service.academicworkload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.academicworkload.repository.model.network.reponse.CsvResponse;
import service.academicworkload.repository.model.network.request.CsvRequest;
import service.academicworkload.service.csv.state.CsvContentType;
import service.academicworkload.service.storage.StorageService;

@RestController
public class CsvController {

    private final StorageService storageService;

    @Autowired
    public CsvController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/api/v1/csv/parse")
    public CsvResponse workloadParsing(@RequestBody CsvRequest request) {
        return switch (request.storage()) {
            case WORKLOAD -> storageService.parseFile(storageService.getWorkload(), CsvContentType.WORKLOAD);
            case DEPARTMENT -> storageService.parseFile(storageService.getDepartments(), CsvContentType.DEPARTMENT);
            case GROUP -> storageService.parseFile(storageService.getGroups(), CsvContentType.GROUP);
            case AUDITORY -> null;
        };
    }


}
