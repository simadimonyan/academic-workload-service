package service.academicworkload.service.csv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvParseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(CsvParseService.class);

    private final Class<T> type;
    private final CsvMapper mapper;
    private final CsvSchema schema;

    public CsvParseService(Class<T> type) {
        this.type = type;
        this.mapper = new CsvMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
        mapper.enable(CsvParser.Feature.TRIM_SPACES);
        mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        this.schema = mapper.schemaFor(type)
                .withHeader()
                .withColumnSeparator(';')
                .withColumnReordering(true);
    }

    public List<T> parse(InputStream file) {
        try {
            String content = new String(file.readAllBytes(), "Windows-1251");
            String headerLine = content.lines().findFirst().orElse("NO HEADER FOUND");
            logger.info("CSV Header columns: {}", headerLine);
            ByteArrayInputStream utf8Stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

            List<T> result = mapper
                    .readerFor(type)
                    .with(schema)
                    .<T>readValues(utf8Stream)
                    .readAll();

            logger.info("Parsed {} records of type {}", result.size(), type.getSimpleName());
            result.forEach(record -> logger.debug("Parsed record: {}", record));
            return result;
        } catch (Exception e) {
            logger.error("Failed to parse CSV for type " + type.getSimpleName(), e);
            return new ArrayList<>();
        }
    }
}
