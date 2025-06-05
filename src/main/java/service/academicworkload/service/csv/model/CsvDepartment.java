package service.academicworkload.service.csv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CsvDepartment {

    @JsonProperty("Код")
    private Integer code;

    @JsonProperty("Кафедра")
    private String name;

}
