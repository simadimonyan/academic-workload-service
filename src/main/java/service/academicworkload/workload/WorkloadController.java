package service.academicworkload.workload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import service.academicworkload.excel.ExcelProcess;
import service.academicworkload.storage.StorageService;

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

            ExcelProcess excelProcess = new ExcelProcess(file);

            ArrayList<String> lists = excelProcess.getWorkbookLists();

            if (!lists.isEmpty()) {
                return new WorkloadResponse("200", lists.toString());
            }
            else
                return new WorkloadResponse("404", "File is empty");

        }

        return new WorkloadResponse("404", "Loaded file does not exit!");
    }

}
