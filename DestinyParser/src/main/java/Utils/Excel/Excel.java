package Utils.Excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author by Ilin_ai on 26.09.2017.
 * Базовый класс для работы с эксель файлом
 */
public abstract class Excel {

    protected XSSFWorkbook resultWorkBook = new XSSFWorkbook();

    /**
     * Выдача страницы по её названию. Если Станицы не существует, то она создаётся и в ней
     * создаётся одна первая строка
     * @param sheetName название страницы
     * @return объект страницы
     */
    public XSSFSheet getSheet(String sheetName){
        XSSFSheet sheet = resultWorkBook.getSheet(sheetName);
        if (sheet == null) {
            sheet = resultWorkBook.createSheet(sheetName);
        }
        return sheet;
    }

    /**
     * Добавление строки в указанной странице
     * @param sheetName название страницы
     * @return созданная строка
     */
    public XSSFRow addRow(String sheetName){
        XSSFSheet sheet = getSheet(sheetName);
        return sheet.createRow(sheet.getLastRowNum() + 1);
    }

    /**
     * Добавление строки в указанной странице
     * @param sheet Страцица
     * @return созданная строка
     */
    public XSSFRow addRow(XSSFSheet sheet){
        return sheet.createRow(sheet.getLastRowNum() + 1);
    }

    /**
     * Запись таблицы эксель в файл
     */
    public void write(){
        try {
            resultWorkBook.write(new FileOutputStream("result.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resultWorkBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}