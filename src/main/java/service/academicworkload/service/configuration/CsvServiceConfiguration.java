package service.academicworkload.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.academicworkload.service.csv.CsvParseService;
import service.academicworkload.service.csv.model.CsvDepartment;
import service.academicworkload.service.csv.model.CsvGroup;
import service.academicworkload.service.csv.model.CsvWorkload;
@Configuration
public class CsvServiceConfiguration {

    @Bean
    public CsvParseService<CsvWorkload> workloadParser() {
        return new CsvParseService<>(CsvWorkload.class);
    }

    @Bean
    public CsvParseService<CsvDepartment> departmentParser() {
        return new CsvParseService<>(CsvDepartment.class);
    }

    @Bean
    public CsvParseService<CsvGroup> groupParser() {
        return new CsvParseService<>(CsvGroup.class);
    }

}