package service.academicworkload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AutoConfiguration
@SpringBootApplication
public class AcademicWorkloadService {

    public static void main(String[] args) {
        SpringApplication.run(AcademicWorkloadService.class, args);
    }

}
