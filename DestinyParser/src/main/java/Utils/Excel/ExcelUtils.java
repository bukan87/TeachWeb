package Utils.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by Ilin_ai on 23.09.2017.
 */
public class ExcelUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

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

    public static Long getLongValue(XSSFCell cell) throws ExcelBadData{
        if (cell == null){
            return null;
        }
        Long result;
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    throw new BadCellData(cell.getColumnIndex() + 1);
                }
                result = (long)cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                result = Long.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                result = null;
                break;
            default:
                throw new BadCellData(cell.getColumnIndex() + 1);
        }
        return result;
    }

    public static String getStringValue(XSSFCell cell) throws BadCellData{
        if (cell == null){
            return null;
        }
        String result;
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                result = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    result = DATE_FORMAT.format(cell.getDateCellValue());
                }else {
                    result = String.valueOf((long) cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                result = null;
                break;
            default:
                throw new BadCellData(cell.getColumnIndex() + 1);
        }
        return result;
    }

    public static Date getDateValue(XSSFCell cell) throws BadCellData{
        if (cell == null){
            return null;
        }
        Date result;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)){
            result = cell.getDateCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            try {
                result = DATE_FORMAT.parse(cell.getStringCellValue());
            } catch (ParseException e) {
                throw new BadCellData(cell.getColumnIndex() + 1);
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK){
            result = null;
        } else {
            throw new BadCellData(cell.getColumnIndex() + 1);
        }
        return result;
    }

    public static Date getDateTimeValue(XSSFCell cell) throws BadCellData{
        if (cell == null){
            return null;
        }
        Date result;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)){
            result = cell.getDateCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
            try {
                result = DATE_TIME_FORMAT.parse(cell.getStringCellValue());
            } catch (ParseException e) {
                throw new BadCellData(cell.getColumnIndex() + 1);
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK){
            result = null;
        } else {
            throw new BadCellData(cell.getColumnIndex() + 1);
        }
        return result;
    }
}