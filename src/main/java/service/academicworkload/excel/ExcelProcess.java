package service.academicworkload.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class ExcelProcess {

    private Workbook workbook;

    public ExcelProcess(InputStream file) {
        try {
            this.workbook = WorkbookFactory.create(file);
        }
        catch (Exception e) {
            workbook = null;
        }
    }

    public ArrayList<String> getWorkbookLists() {

        ArrayList<String> sheetNames = new ArrayList<>();
        for (int i=0; i<workbook.getNumberOfSheets(); i++) {
            sheetNames.add(workbook.getSheetName(i) );
        }

        return sheetNames;
    }

}
