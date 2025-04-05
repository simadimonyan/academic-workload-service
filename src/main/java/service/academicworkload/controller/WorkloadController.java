package service.academicworkload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import service.academicworkload.controller.dto.Response;
import service.academicworkload.domain.ExcelProcess;
import service.academicworkload.service.StorageService;

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
    public Response workloadParsing() {

        InputStream file = storageService.getWorkloadInputStream();

        if (file != null) {

            ExcelProcess excelProcess = new ExcelProcess(file);

            ArrayList<String> lists = excelProcess.getWorkbookLists();

            if (!lists.isEmpty()) {
                return new Response("200", lists.toString());
            }
            else
                return new Response("404", "File is empty");

        }

        return new Response("404", "Loaded file does not exit!");
    }

}
