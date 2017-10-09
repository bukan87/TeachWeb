package settings;

import Utils.Excel.BadCellData;
import Utils.Excel.ExcelUtils;
import Utils.Excel.ResultType;
import model.GameType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author by Ilin_ai on 22.09.2017.
 * Класс параметров из settings.xlsx
 */
public class Settings {
    private static Settings ourInstance = new Settings();
    private XSSFWorkbook workbook;
    private Map<GameType, Map<String, SettingValue>> sheets = new HashMap<>();
    private Date startDate;
    private Date endDate;
    private ResultType resultType;
    private String clanName;

    public static Settings getInstance() {
        return ourInstance;
    }

    /**
     * При создании вычитываем параметры
     */
    private Settings() {
        try {
            FileInputStream fis = new FileInputStream(new File("settings.xlsx"));
            workbook = new XSSFWorkbook(fis);
            fis.close();
            XSSFRow params = workbook.getSheet("Params").getRow(1);
            startDate = getDate(params.getCell(0));
            endDate = getDate(params.getCell(1));
            switch (params.getCell(2).getStringCellValue()){
                case "Игры":
                    resultType = ResultType.GAMES;
                    break;
                case "Статистика":
                    resultType = ResultType.STATISTIC;
                    break;
                default:
                    throw new BadCellValue("Params", 2);
            }
            clanName = params.getCell(3).getStringCellValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Вытаскивание даты из входных парметров
     * @param cell ячейка, в которой должна храниться дата
     * @return дата в виде строки(может быть null)
     */
    private Date getDate(XSSFCell cell){
        Date result = null;
        try{
            result = ExcelUtils.getDateValue(cell);
        }catch (BadCellData badCellData){
            throw new BadCellValue("Params", cell.getColumnIndex() + 1);
        }
        return result;
    }

    /**
     * Выдача параметров для конкретной игры
     * @param gameType тип игры
     * @return Мап имя параметра : SettingValue
     */
    public Map<String, SettingValue> getGameParams(GameType gameType){
        if (sheets.containsKey(gameType)){
            return sheets.get(gameType);
        }
        Map<String, SettingValue> result = new LinkedHashMap<>();
        XSSFSheet sheet = workbook.getSheet(gameType.settingsSheetName);
        for (int i = 1; i < sheet.getLastRowNum(); i++){
            XSSFRow row = sheet.getRow(i);
            // Если указано значение отображения на русском, то считаем, что параметр учитывается
            if (!isNull(row.getCell(0)) && !isNull(row.getCell(1))){
                SettingValue sv = new SettingValue();
                sv.setRussianName(row.getCell(1).getStringCellValue());
                sv.setDatatype(Datatype.valueOf(row.getCell(2).getStringCellValue()));
                if (sv.getDatatype() != Datatype.STRING) {
                    sv.setAggregateOperation(AggregateOperation.valueOf(row.getCell(3).getStringCellValue()));
                }
                result.put(row.getCell(0).getStringCellValue(), sv);
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getClanName() {
        return clanName;
    }

    public ResultType getResultType() {
        return resultType;
    }

    private class BadCellValue extends RuntimeException{
        public BadCellValue(String sheetName, int cellNum){
            super("Указано не корректное значение на странице " + sheetName + " в ячейке " + cellNum);
        }
    }
}