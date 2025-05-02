package service.academicworkload.service.csv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import service.academicworkload.service.csv.model.parent.CsvModel;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workload extends CsvModel {

    @JsonProperty("Группа")
    private String group;

    @JsonProperty("Факультет")
    private String faculty;

    @JsonProperty("Курс")
    private Integer course;

    @JsonProperty("ФИО")
    private String teacher;

    @JsonProperty("КодКафедры")
    private Integer departmentId;

    @JsonProperty("Семестр")
    private Integer semester;

    @JsonProperty("Дисциплина")
    private String subject;

    @JsonProperty("ВидРабот")
    private String subjectType;

    @JsonProperty("Часов")
    private Integer hours;

    @JsonProperty("СтатусПрепода")
    private String teacherStatus;

    @JsonProperty("Недель")
    private Integer weeks;

}
