package service.academicworkload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import service.academicworkload.repository.model.network.response.CsvResponse;
import service.academicworkload.service.csv.model.CsvDepartment;
import service.academicworkload.service.csv.model.CsvGroup;
import service.academicworkload.service.csv.model.CsvWorkload;
import service.academicworkload.service.process.WorkloadFactoryService;
import service.academicworkload.service.storage.StorageService;

import java.util.ArrayList;

@RestController
public class CsvController {

    private final StorageService storageService;
    private final WorkloadFactoryService workloadFactory;

    @Autowired
    public CsvController(StorageService storageService, WorkloadFactoryService workloadFactory) {
        this.storageService = storageService;
        this.workloadFactory = workloadFactory;
    }

    @PostMapping("/api/v1/csv/parse")
    public CsvResponse workloadParsing() {
        ArrayList<CsvWorkload> workloads = storageService.parseWorkloadFile();
        ArrayList<CsvDepartment> departments = storageService.parseDepartmentFile();
        ArrayList<CsvGroup> groups = storageService.parseGroupFile();

        if (workloads != null && departments != null && groups != null) {
            try {
                workloadFactory.process(workloads, groups, departments);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return new CsvResponse("500", "Unknown error", e.getMessage());
            }
            return new CsvResponse("200", "Parsed", "");
        } else {
            return new CsvResponse("404", "File is empty or does not exist!", "");
        }
    }

}
