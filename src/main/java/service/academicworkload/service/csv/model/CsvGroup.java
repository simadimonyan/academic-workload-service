package service.academicworkload.service.csv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CsvGroup {

    @JsonProperty("Группа")
    private String group;

    @JsonProperty("Факультет")
    private String faculty;

    @JsonProperty("Курс")
    private Integer course;

    @JsonProperty("Студентов")
    private Integer capacity;

    @JsonProperty("ФормаОбучения")
    private Integer studyForm;

    @JsonProperty("НачалоТО1")
    private String periodStart1;

    @JsonProperty("КонецТО1")
    private String periodEnd1;

    @JsonProperty("НачалоТО2")
    private String periodStart2;

    @JsonProperty("КонецТО2")
    private String periodEnd2;

    @JsonProperty("НачалоТО3")
    private String periodStart3;

    @JsonProperty("КонецТО3")
    private String periodEnd3;

    @JsonProperty("НачалоТО4")
    private String periodStart4;

    @JsonProperty("КонецТО4")
    private String periodEnd4;

}
