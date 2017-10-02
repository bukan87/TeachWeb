package Utils.Excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author by Ilin_ai on 23.09.2017.
 */
public class ExcelUtils {

    /**
     * Центрование контента ячеки
     * @param cell ячейка
     */
    public static void alignCenter(XSSFCell cell){
        if (cell != null) {
            XSSFCellStyle style = cell
                    .getRow()
                    .getSheet()
                    .getWorkbook()
                    .createCellStyle();
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(style);
        }
    }

    /**
     * Объединение ячеек
     * @param cell ячека, которую необходимо объединить с дургими
     * @param x сколько ячеек нужно объединить по оси X
     * @param y сколько ячеек нужно объединить по оси Y
     */
    public static void mergeCells(XSSFCell cell, int x, int y){
        XSSFSheet currentSheet = cell.getSheet();

        int cellX;
        int cellY;

        int cellXMerge;
        int cellYMerge;

        if (x < 0){
            cellX = cell.getRowIndex() + x;
            cellXMerge = cell.getRowIndex();
        }else{
            cellX = cell.getRowIndex();
            cellXMerge = cell.getRowIndex() + x;
        }

        if (y < 0){
            cellY = cell.getColumnIndex() + y;
            cellYMerge = cell.getColumnIndex();
        }else{
            cellY = cell.getColumnIndex();
            cellYMerge = cell.getColumnIndex() + y;
        }

        CellRangeAddress mergedRange = new CellRangeAddress(cellX, cellXMerge, cellY, cellYMerge);
        currentSheet.addMergedRegion(mergedRange);
    }
}
