import Utils.BungieMethods;
import Utils.Excel.ExcelUtils;
import Utils.Excel.ResultExcel;
import Utils.Settings;
import com.sun.org.apache.regexp.internal.RE;
import model.Character;
import model.Event;
import model.GameType;
import model.Player;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class Main {
    private static BungieMethods bungieMethods = new BungieMethods();

    public static void main(String[] args){

        int exit = 0;
        //gameIndicators();
        for (long playerId : bungieMethods.getClanMembersId()){
            for (long characterId : bungieMethods.getCharactersId(playerId)) {
                // Просмотрим игры, в которых участвовал персонаж
                events(playerId, 2, characterId, GameType.TRAILS_OF_NINE);
                events(playerId, 2, characterId, GameType.RAID);
                events(playerId, 2, characterId, GameType.NIGHTFALL);
            }
            //if (exit++ == 10) break;
        }
        ResultExcel.getInstance().write();
    }

    private static void gameIndicators(){
        for(Player player : bungieMethods.getClanMembers()){
            // Пробежимся по персонажам игрока
            //int playerMerge = 0;
            Map<GameType, Integer> mergeDays = new HashMap<>();
            for(int i = 0; i < player.getCharacters().size(); i++){
                for (GameType gameType : GameType.values()) {
                    if (!mergeDays.containsKey(gameType)){
                        mergeDays.put(gameType, 0);
                    }
                    int characterMerge = 0;
                    Character character = player.getCharacters().get(i);
                    XSSFRow row = createRow(player, gameType, character);
                    int cellNum = row.getLastCellNum();

                    // Показатели игры
                    Map<String, Map<String, String>> gameIndicators = character.getGameIndicators().get(gameType);
                    if (gameIndicators != null) {
                        if (gameIndicators.size() > 1) {
                            //mergeDays.put(g)
                            mergeDays.put(gameType, mergeDays.get(gameType) + gameIndicators.size() - 1);
                        }
                        // Показатели храняться по дням
                        Map<String, String> settings = Settings.getInstance().getGameParams(gameType);
                        for (Map.Entry<String, Map<String, String>> day : gameIndicators.entrySet()){
                            characterMerge++;
                            if (characterMerge > 1) {
                                row = createRow(player, gameType, character);
                                cellNum = row.getLastCellNum();
                            }
                            row.createCell(cellNum++).setCellValue(day.getKey());
                            for (Map.Entry<String, String> setting : settings.entrySet()) {
                                row.createCell(cellNum++).setCellValue(day.getValue().get(setting.getKey()));
                            }
                        }
                        if (characterMerge > 1){
                            ExcelUtils.mergeCells(row.getCell(1), -1 * (characterMerge - 1), 0);
                            ExcelUtils.mergeCells(row.getCell(2), -1 * (characterMerge - 1), 0);
                        }
                    }
                    // Если персонажей было несколько, то необходимо объединить ячейку с именем
                    if ((mergeDays.get(gameType) + player.getCharacters().size()) > 1 && i == player.getCharacters().size() - 1) {
                        ExcelUtils.mergeCells(row.getCell(0), -1 * (mergeDays.get(gameType) + player.getCharacters().size() - 1), 0);
                    }
                }
            }
        }
    }

    /**
     * Создание строки в отчёте
     * @param player
     * @param gameType
     * @param character
     * @return
     */
    private static XSSFRow createRow(Player player, GameType gameType, Character character){
        XSSFRow row = ResultExcel.getInstance().addRow(gameType);
        int cellNum = 0;
        row.createCell(cellNum++).setCellValue(player.getName());
        row.createCell(cellNum++).setCellValue(String.valueOf(character.getId()));
        row.createCell(cellNum++).setCellValue(character.getLight());
        for (int i = 0; i < cellNum; i++) {
            ExcelUtils.alignCenter(row.getCell(i));
        }
        return row;
    }

    private static void events(long playerId, int memberType, long characterId, GameType gameType){
        for (Event event : bungieMethods.getEvents(playerId, memberType, characterId, gameType)){
            XSSFRow eventRow = null;
            for (Player eventPlayer : event.getTeammates()){
                eventRow = ResultExcel.getInstance().addRow("Games_" + gameType.settingsSheetName);
                int eventCellNum = 0;
                eventRow.createCell(eventCellNum++).setCellValue(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(event.getEventDate()));
                eventRow.createCell(eventCellNum++).setCellValue(eventPlayer.getName());
                eventRow.createCell(eventCellNum++).setCellValue(eventPlayer.getClan().getName());
                ExcelUtils.alignCenter(eventRow.getCell(0));
            }
            if (event.getTeammates().size() > 1 && eventRow != null) {
                ExcelUtils.mergeCells(eventRow.getCell(0), -1 * event.getTeammates().size() + 1, 0);
            }
        }
    }
}
