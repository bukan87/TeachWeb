package Utils.Excel;

import Utils.Settings;
import model.GameType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class ResultExcel extends Excel {

    private static ResultExcel instance = new ResultExcel();

    private ResultExcel(){
    }

    public static ResultExcel getInstance(){
        return instance;
    }

    /**
     * Доабвление строки на странице отчёта
     * @param gameType тип игры
     * @return созданная строка
     */
    public XSSFRow addRow(GameType gameType){
        XSSFSheet sheet = getSheet(gameType);
        return sheet.createRow(sheet.getLastRowNum() + 1);
    }

    /**
     * Выдача страницы отчёта
     * @param gameType тип игры
     * @return страница
     */
    public XSSFSheet getSheet(GameType gameType){
        XSSFSheet result = getSheet(gameType.settingsSheetName);
        if (result.getRow(0) == null){
            setHeader(gameType);
        }
        return result;
    }

    /**
     * Задание заголовка
     * @param gameType тип игры
     */
    private void setHeader(GameType gameType){
        XSSFSheet sheet = getSheet(gameType.settingsSheetName);
        XSSFRow headerRow = sheet.createRow(0);
        int cellNum = 0;
        // Общие атрибуты
        headerRow.createCell(cellNum++).setCellValue("Имя бойца");
        headerRow.createCell(cellNum++).setCellValue("Персонаж");
        headerRow.createCell(cellNum++).setCellValue("Лайт");
        headerRow.createCell(cellNum++).setCellValue("День");

        // Показатели игры
        Map<String, String> settings = Settings.getInstance().getGameParams(gameType);
        for(Map.Entry<String, String> setting : settings.entrySet()){
            headerRow.createCell(cellNum++).setCellValue(setting.getValue());
        }
    }
}