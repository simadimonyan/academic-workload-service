package service.academicworkload.service.csv;

import service.academicworkload.service.csv.model.parent.CsvModel;
import service.academicworkload.service.csv.state.CsvContentType;

public class CsvParseFactory {

    public static <T extends CsvModel> CsvParseService<T> create(CsvContentType<T> type) {
        return new CsvParseService<>(type.getFormat());
    }

}
