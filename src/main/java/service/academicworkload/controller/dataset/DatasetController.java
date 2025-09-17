package service.academicworkload.controller.dataset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasetController {

//    @Autowired
//    public DatasetController() {
//
//    }

    @GetMapping("/api/v1/dataset/groups")
    public String loadAllGroups() {

        return "";
    }

    // /api/v1/dataset/group?name=23-ЗИВТ-02&weeks=true (загрузить датасет с распределением нагрузки по неделям)
    @GetMapping("/api/v1/dataset/group/{name}")
    public String loadGroup(@PathVariable String name, @PathVariable(required = false) Boolean weeks) {



        return "";
    }

}
