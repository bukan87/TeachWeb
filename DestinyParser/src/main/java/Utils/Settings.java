package Utils;

import model.GameType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by Ilin_ai on 22.09.2017.
 * Класс параметров из settings.xlsx
 */
public class Settings {
    private static Settings ourInstance = new Settings();
    private XSSFWorkbook workbook;
    private Map<GameType, Map<String, String>> sheets = new HashMap<>();

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
        try {
            FileInputStream fis = new FileInputStream(new File("settings.xlsx"));
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выдача параметров для конкретной игры
     * @param gameType тип игры
     * @return Мап имя параметра : отображение в отчёте
     */
    public Map<String, String> getGameParams(GameType gameType){
        if (sheets.containsKey(gameType)){
            return sheets.get(gameType);
        }
        Map<String, String> result = new LinkedHashMap<>();
        XSSFSheet sheet = workbook.getSheet(gameType.settingsSheetName);
        for (int i = 1; i < sheet.getLastRowNum(); i++){
            XSSFRow row = sheet.getRow(i);
            if (!isNull(row.getCell(0)) && !isNull(row.getCell(1))){
                result.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
            }
        }
        sheets.put(gameType, result);
        return result;
    }

    /**
     * Проверка на пустоту ячейки
     * @param cell ячейка
     */
    private static boolean isNull(XSSFCell cell){
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || cell.getStringCellValue() == null){
            return true;
        }
        return false;
    }
}