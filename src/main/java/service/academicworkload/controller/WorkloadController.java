package service.academicworkload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import service.academicworkload.repository.model.network.WorkloadResponse;
import service.academicworkload.service.csv.CsvParseFactory;
import service.academicworkload.service.csv.CsvParseService;
import service.academicworkload.service.csv.model.Workload;
import service.academicworkload.service.csv.state.CsvContentType;
import service.academicworkload.service.storage.StorageService;

import java.io.InputStream;
import java.util.ArrayList;

@RestController
public class WorkloadController {

    private final StorageService storageService;

    @Autowired
    public WorkloadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PatchMapping("/api/v1/workload/update")
    public WorkloadResponse workloadParsing() {

        InputStream file = storageService.getWorkloadInputStream();

        if (file != null) {

            CsvParseService<Workload> service = CsvParseFactory.create(CsvContentType.WORKLOAD);
            ArrayList<Workload> lists = (ArrayList<Workload>) service.parse(file);

            if (!lists.isEmpty()) {
                return new WorkloadResponse("200", lists.get(1).toString());
            }
            else
                return new WorkloadResponse("404", "File is empty");

        }

        return new WorkloadResponse("404", "Loaded file does not exit!");
    }

}
