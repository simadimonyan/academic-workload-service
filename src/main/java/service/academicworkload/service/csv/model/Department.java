package service.academicworkload.service.csv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import service.academicworkload.service.csv.model.parent.CsvModel;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department extends CsvModel {

    @JsonProperty("Код")
    private Integer code;

    @JsonProperty("Кафедра")
    private String name;

}
