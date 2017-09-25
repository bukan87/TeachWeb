package Utils;

import model.Character;
import model.GameType;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class BungieMethods {
    private static final String BASE_URL = "https://www.bungie.net/Platform/";
    private static final String CLAN_NAME = "Russian Warriors";
    private long clanId = 0L;

    private JSONObject basicGet(String addPath){
        String url = BASE_URL + addPath;
        JSONObject result = new JSONObject(HTTPUtils.SendGet(url));
        if (!result.has("Response")){
            System.out.println("Нет Response");
            System.out.println(result);
        }else {
            result = result.getJSONObject("Response");
        }
        return result;
    }

    /**
     * Определение ИД клана
     * @return ид клана
     */
    public long getClanId(){
        if (clanId == 0L){
            clanId = basicGet("GroupV2/Name/" + CLAN_NAME + "/1/").getJSONObject("detail").getLong("groupId");
        }
        return clanId;
    }

    /**
     * Выдача списка участников клана
     * @return список участников клана
     */
    public List<Player> getClanMembers(){
        int page = 0;
        boolean isExistsPage = true;
        List<Player> players = new ArrayList<>();

        while (isExistsPage) {
            page++;
            JSONObject obj = basicGet("/GroupV2/" + getClanId() + "/Members/?currentPage=" + page);
            JSONArray array = obj.getJSONArray("results");
            if (array.length() > 0) {
                int exit = 0;
                for (int i = 0; i < array.length(); i++) {
                    //if (exit++ == 20) break;

                    JSONObject playerObject = (JSONObject)array.get(i);
                    Player player = new Player();
                    player.setId(playerObject.getJSONObject("destinyUserInfo").getLong("membershipId"));
                    player.setName(playerObject.getJSONObject("destinyUserInfo").getString("displayName"));

                    System.out.println(player.getName());

                    player.setMembershipType(playerObject.getJSONObject("destinyUserInfo").getInt("membershipType"));
                    player.getCharacters().addAll(getCharacters(player.getId(), player.getMembershipType()));
                    players.add(player);
                }
            }
            // Проверка наличия страниц далее
            isExistsPage = obj.getBoolean("hasMore");
        }

        // Отсортируем список по имени пользователя
        players.sort((param1, param2) -> {
            if (param1 == null) {
                if (param2 == null) {
                    return 0; // Both students are null
                } else {
                    return -1; // paramT1 is NULL, so put paramT1 in the end of
                    // the sorted list
                }
            } else {
                if (param2 == null) {
                    return 1;
                }
            }
            String name1 = param1.getName().toLowerCase();
            String name2 = param2.getName().toLowerCase();
            return name1.compareTo(name2);
        });
        return players;
    }

    /**
     * Выборка персонажей по пользователю
     */
    private List<Character> getCharacters(long id, int memberType){
        List<Character> result = new ArrayList<>();
        // Достанем информацию по пользователю, а из неё список персонажей
        JSONArray characters = basicGet("/Destiny2/" + memberType + "/Profile/" + id + "/?components=100")
                .getJSONObject("profile")
                .getJSONObject("data")
                .getJSONArray("characterIds");
        // Вытащим информацию по каждому персонажу
        for (int i = 0; i < characters.length(); i++){
            Character character = new Character();
            character.setId(Long.parseLong((String)characters.get(i)));

            System.out.println(character.getId());

            // Вытащим текущий лайт
            int light = basicGet("/Destiny2/" + memberType + "/Profile/" + id + "/Character/" + character.getId() + "/?components=200")
                    .getJSONObject("character")
                    .getJSONObject("data")
                    .getInt("light");
            character.setLight(light);
            Map<GameType, Map<String, String>> gameIndicators = character.getGameIndicators();
            for (GameType gameType : GameType.values()){
                gameIndicators.put(gameType, getCharacterGameIndicators(id, memberType, character.getId(), gameType));
            }
            result.add(character);
        }
        return result;
    }

    /**
     * Определение показателей игры
     * @param playerId идентификатор игрока
     * @param memberType типа учатсника
     * @param characterId идентификатор персонажа
     * @param gameType тип игры
     * @return массив тип показателя: значение
     */
    public Map<String, String> getCharacterGameIndicators(long playerId, int memberType, long characterId, GameType gameType){
        JSONObject indicators = basicGet("Destiny2/" + memberType + "/Account/" + playerId + "/Character/" + characterId
                + "/Stats/?periodType=AllTime&modes=" + gameType.paramName).getJSONObject(gameType.keyName);
        System.out.println(indicators);
        if (!indicators.has("allTime")){
            System.out.println("У персонажа " + characterId + " нет метрик по типу игры " + gameType.paramName);
            return new HashMap<>();
        }
        indicators = indicators.getJSONObject("allTime");

        Map<String, String> result = new HashMap<>();
        for (String indicator : Settings.getInstance().getGameParams(gameType).keySet()){
            result.put(indicator, indicators.getJSONObject(indicator)
                    .getJSONObject("basic")
                    .getString("displayValue"));
        }
        return result;
    }
}