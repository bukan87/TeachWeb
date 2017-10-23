import Utils.BungieMethods;
import Utils.Excel.ExcelUtils;
import Utils.Excel.ResultExcel;
import Utils.Excel.ResultType;
import model.Character;
import model.Event;
import model.GameType;
import model.Player;
import org.apache.poi.xssf.usermodel.XSSFRow;
import settings.SettingValue;
import settings.Settings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class Main {
    private static BungieMethods bungieMethods = new BungieMethods();

    public static void main(String[] args){
        if (Settings.getInstance().getResultType() == ResultType.STATISTIC) {
            gameIndicators();
        } else {
            int exit = 0;
            for (long playerId : bungieMethods.getClanMembersId()) {
                for (long characterId : bungieMethods.getCharactersId(playerId)) {
                    // Просмотрим игры, в которых участвовал персонаж
                    events(playerId, 2, characterId, GameType.TRAILS_OF_NINE);
                    events(playerId, 2, characterId, GameType.RAID);
                    events(playerId, 2, characterId, GameType.NIGHTFALL);
                }
                //if (exit++ == 1) break;
            }
        }
        ResultExcel.getInstance().write();
    }

    private static <T> T cast(Class<T> clazz, Object data){
        return (T)data;
    }

    private static void gameIndicators(){
        for(Player player : bungieMethods.getClanMembers()){
            // Пробежимся по персонажам игрока
            //int playerMerge = 0;
            for(int i = 0; i < player.getCharacters().size(); i++){
                for (GameType gameType : GameType.values()) {
                    Character character = player.getCharacters().get(i);
                    XSSFRow row = createRow(player, gameType, character);
                    int cellNum = row.getLastCellNum();

                    // Показатели игры
                    Map<String, Map<String, String>> gameIndicators = character.getGameIndicators().get(gameType);
                    if (gameIndicators != null) {
                        // Показатели храняться по дням, нужно просуммировать данные или найти среднее
                        Map<String, SettingValue> settings = Settings.getInstance().getGameParams(gameType);
                        Map<String, List<Object>> indicatorsMap = new LinkedHashMap<>();
                        for (Map.Entry<String, Map<String, String>> day : gameIndicators.entrySet()){
                            for (Map.Entry<String, SettingValue> setting : settings.entrySet()) {
                                if (!indicatorsMap.containsKey(setting.getKey())){
                                    indicatorsMap.put(setting.getKey(), new ArrayList<>());
                                }
                                if (day.getValue().get(setting.getKey()) != null) {
                                    List<Object> indicatorValues = indicatorsMap.get(setting.getKey());
                                    indicatorValues.add(day.getValue().get(setting.getKey()));
                                }
                            }
                        }
                        // Теперь сагрегируем все данные и запишем в отчёт
                        for (String data : aggregate(gameType, indicatorsMap)){
                            row.createCell(cellNum++).setCellValue(data);
                        }
                    }
                    // Если персонажей было несколько, то необходимо объединить ячейку с именем
                    if ( + player.getCharacters().size() > 1 && i == player.getCharacters().size() - 1) {
                        ExcelUtils.mergeCells(row.getCell(0), -1 * (player.getCharacters().size() - 1), 0);
                    }
                }
            }
        }
    }

    /**
     * Аггрегация данных
     * @param gameType тип игры, исходя из этого будет браться конфиг
     * @param dataMap массив данных
     * @return Лист аггрегированных данных, переведённых в строку
     */
    private static List<String> aggregate(GameType gameType, Map<String, List<Object>> dataMap){
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, SettingValue> setting : Settings.getInstance().getGameParams(gameType).entrySet()){
            if (dataMap.containsKey(setting.getKey()) && dataMap.get(setting.getKey()).size() > 0){
                try {
                    List<Object> dataList = dataMap.get(setting.getKey());
                    switch (setting.getValue().getDatatype()) {
                        case DOUBLE: {
                            DoubleStream stream = dataList.stream().mapToDouble(value -> Double.valueOf(value.toString()));
                            switch (setting.getValue().getAggregateOperation()) {
                                case AVG:
                                    result.add(String.valueOf(stream.average().getAsDouble()));
                                    break;
                                case MAX:
                                    result.add(String.valueOf(stream.max().getAsDouble()));
                                    break;
                                case MIN:
                                    result.add(String.valueOf(stream.min().getAsDouble()));
                                    break;
                                case SUM:
                                    result.add(String.valueOf(stream.sum()));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                        case LONG: {
                            LongStream stream = dataList.stream().mapToLong(value -> Long.valueOf(value.toString()));
                            switch (setting.getValue().getAggregateOperation()) {
                                case AVG:
                                    result.add(String.valueOf(new Double(stream.average().getAsDouble()).longValue()));
                                    break;
                                case MAX:
                                    result.add(String.valueOf(stream.max().getAsLong()));
                                    break;
                                case MIN:
                                    result.add(String.valueOf(stream.min().getAsLong()));
                                    break;
                                case SUM:
                                    result.add(String.valueOf(stream.sum()));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                        case DURATION: {
                            // Сначала трансформируем строку времени в количество секунд и сагрегируем
                            LongStream stream = dataList.stream().mapToLong(value -> parseDateToSeconds(value.toString()));
                            Long subResult = null;
                            switch (setting.getValue().getAggregateOperation()) {
                                case AVG:
                                    subResult = new Double(stream.average().getAsDouble()).longValue();
                                    break;
                                case MAX:
                                    subResult = stream.max().getAsLong();
                                    break;
                                case MIN:
                                    subResult = stream.min().getAsLong();
                                    break;
                                case SUM:
                                    subResult = stream.sum();
                                    break;
                                default:
                                    break;
                            }
                            // Трансформируем обратно в строку
                            result.add(transformSecondToString(subResult));
                            break;
                        }
                        case STRING: {
                            result.add(dataList.get(0).toString());
                            break;
                        }
                    }
                } catch (Exception e){
                    System.out.println(gameType + " " + setting.getValue().getRussianName() + " " + dataMap.get(setting.getKey()).toString());
                    throw e;
                }
            } else {
                result.add("");
            }

        }
        return result;
    }

    /**
     * Создание строки в отчёте
     * @param player игрок
     * @param gameType тип игры(страницыв в отчёте)
     * @param character персонаж
     * @return созданная строка
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

    /**
     * Преобразование секунд в строку
     * @param s секнды
     * @return время
     */
    private static String transformSecondToString(Long s){
        Long day = s / 86400;
        Long hours = (s / 3600) - 24 * day;
        Long minutes = (s % 3600) / 60;
        Long seconds = s % 60;
        StringBuilder result = new StringBuilder();
        if (day > 0)
            result.append(day + "day");
        if (hours > 0){
            result.append(" " + hours + "h");
        }
        if (minutes > 0){
            result.append(" " + minutes + "m");
        }
        result.append(seconds+ "sec ");
        return result.toString().trim();
    }

    /**
     * Парс количества секунд из строковой даты
     * @param date строковая дата
     * @return количество секунд
     */
    private static Long parseDateToSeconds(String date){
        String[] times = date.split(" ");
        int factor = 1;
        long result = 0;
        for (String time : date.split(" ")){
            switch (time.replaceAll("\\d", "")){
                case "d":
                    factor = 86400;
                    break;
                case "h":
                    factor = 3600;
                    break;
                case "m":
                    factor = 60;
                    break;
                default:
                    factor = 1;
                    break;
            }
            result += Integer.parseInt(time.replaceAll("\\D", "")) * factor;
        }
        return result;
    }
}