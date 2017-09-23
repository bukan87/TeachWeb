import Utils.BungieMethods;
import Utils.ExcelUtils;
import Utils.ResultExcel;
import Utils.Settings;
import model.Character;
import model.GameType;
import model.Player;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class Main {

    public static void main(String[] args){
        BungieMethods bungieMethods = new BungieMethods();

        for(Player player : bungieMethods.getClanMembers()){
            // Пробежимся по персонажам игрока
            for(int i = 0; i < player.getCharacters().size(); i++){
                for (GameType gameType : GameType.values()) {
                    XSSFRow row = ResultExcel.getInstance().addRow(gameType);
                    int cellNum = 0;
                    row.createCell(cellNum++).setCellValue(player.getName());
                    ExcelUtils.alignCenter(row.getCell(0));
                    Character character = player.getCharacters().get(i);
                    row.createCell(cellNum++).setCellValue(String.valueOf(character.getId()));
                    row.createCell(cellNum++).setCellValue(character.getLight());

                    // Показатели игры
                    Map<String, String> gameIndicators = character.getGameIndicators().get(gameType);
                    if (gameIndicators != null) {
                        Map<String, String> settings = Settings.getInstance().getGameParams(gameType);
                        for (Map.Entry<String, String> setting : settings.entrySet()) {
                            row.createCell(cellNum++).setCellValue(gameIndicators.get(setting.getKey()));
                        }
                    }

                    // Если персонажей было несколько, то необходимо объединить ячейку с именем
                    if (player.getCharacters().size() > 1 && i == player.getCharacters().size() - 1) {
                        ExcelUtils.mergeCells(row.getCell(0), -1 * player.getCharacters().size() + 1, 0);
                    }
                }
            }
        }
        ResultExcel.getInstance().write();
    }
}
