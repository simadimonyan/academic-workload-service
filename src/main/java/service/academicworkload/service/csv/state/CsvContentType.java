package service.academicworkload.service.csv.state;

import lombok.Getter;
import service.academicworkload.service.csv.model.Department;
import service.academicworkload.service.csv.model.Group;
import service.academicworkload.service.csv.model.Workload;
import service.academicworkload.service.csv.model.parent.CsvModel;

@Getter
public final class CsvContentType<T extends CsvModel> {

    public static final CsvContentType<Department> DEPARTMENT = new CsvContentType<>(Department.class);
    public static final CsvContentType<Workload> WORKLOAD = new CsvContentType<>(Workload.class);
    public static final CsvContentType<Group> GROUP = new CsvContentType<>(Group.class);

    private final Class<T> format;
    private CsvContentType(Class<T> format) {
        this.format = format;
    }

}
