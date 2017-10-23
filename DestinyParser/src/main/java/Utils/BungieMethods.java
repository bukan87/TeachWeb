package Utils;

import settings.Settings;
import model.Character;
import model.Clan;
import model.Event;
import model.GameType;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
// TODO здесь должны быть только методы от сайта. Остальную логику нужно перенести в другой класс
public class BungieMethods {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String BASE_URL = "https://www.bungie.net/Platform/";
    private static final String CLAN_NAME = Settings.getInstance().getClanName();
    private long clanId = 0L;
    private List<Long> loadedEvents = new ArrayList<>();

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
     * Определение клана по игроку
     * @param playerId идентификатор игрока
     * @param memberType тип игрока
     * @return Клан
     */
    public Clan getClanByMember(long playerId, int memberType){
        Clan clan = new Clan();
        JSONObject clanInfo = basicGet("/GroupV2/User/" + memberType + "/" + playerId + "/all/1/");
        if (clanInfo.getJSONArray("results").length() == 0){
            // TODO nullPointer
            return new Clan();
        }
        clanInfo = ((JSONObject)clanInfo.getJSONArray("results").get(0)).getJSONObject("group");
        clan.setClanID(clanInfo.getLong("groupId"));
        clan.setName(clanInfo.getString("name"));
        return clan;
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
                    //if (exit++ == 2) break;

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
     * Выдача идентификаторов участников клана
     */
    public List<Long> getClanMembersId(){
        int page = 0;
        boolean isExistsPage = true;
        List<Long> playersId = new ArrayList<>();

        while (isExistsPage) {
            page++;
            JSONObject obj = basicGet("/GroupV2/" + getClanId() + "/Members/?currentPage=" + page);
            JSONArray array = obj.getJSONArray("results");
            if (array.length() > 0) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject playerObject = (JSONObject)array.get(i);
                    playersId.add(playerObject.getJSONObject("destinyUserInfo").getLong("membershipId"));
                }
            }
            // Проверка наличия страниц далее
            isExistsPage = obj.getBoolean("hasMore");
        }
        return playersId;
    }

    public List<Long> getCharactersId(long playerId){
        List<Long> charactersId = new ArrayList<>();
        JSONArray characters = basicGet("/Destiny2/" + 2 + "/Profile/" + playerId + "/?components=100")
                .getJSONObject("profile")
                .getJSONObject("data")
                .getJSONArray("characterIds");
        for (Object character : characters) {
            charactersId.add(Long.valueOf((String)character));
        }

        return charactersId;
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
            Map<GameType, Map<String, Map<String, String>>> gameIndicators = character.getGameIndicators();
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
    public Map<String, Map<String, String>> getCharacterGameIndicators(long playerId, int memberType, long characterId, GameType gameType){
        String params = "&modes=" + gameType.paramName;
        if (Settings.getInstance().getStartDate() == null && Settings.getInstance().getEndDate() == null){
            params += "&periodType=AllTime";
        }else {
            params += "&periodType=Daily";
            if (Settings.getInstance().getStartDate() != null){
                params += "&daystart=" + DATE_FORMAT.format(Settings.getInstance().getStartDate());
            }
            if (Settings.getInstance().getEndDate() != null){
                params += "&dayend=" + DATE_FORMAT.format(Settings.getInstance().getEndDate());
            }
        }
        JSONObject indicators = basicGet("Destiny2/" + memberType + "/Account/" + playerId + "/Character/" + characterId
                + "/Stats/?&" + params).getJSONObject(gameType.keyName);
        System.out.println(indicators);
        if (indicators.has("allTime")) {
            indicators = indicators.getJSONObject("allTime");
            Map<String, Map<String, String>> result = new HashMap<>();
            result.put("all", getCharacterGameIndicator(indicators, gameType));
            return result;
        } else if (indicators.has("daily")){
            Map<String, Map<String, String>> result = new LinkedHashMap<>();
            for (Object daily : indicators.getJSONArray("daily")) {
                JSONObject day = (JSONObject) daily;
                String date = day.getString("period").substring(0, 10);
                result.put(date, getCharacterGameIndicator(day.getJSONObject("values"), gameType));
            }
            return result;
        }else {
            System.out.println("У персонажа " + characterId + " нет метрик по типу игры " + gameType.paramName);
            return new HashMap<>();
        }
    }

    /**
     * Парсинг показателей игры
     * @param indicators объект, который необходимо распарсить
     * @param gameType тип игры
     * @return Map тип показателя - значение
     */
    private Map<String, String> getCharacterGameIndicator(JSONObject indicators, GameType gameType){
        Map<String, String> result = new HashMap<>();
        for (String indicator : Settings.getInstance().getGameParams(gameType).keySet()){
            if (indicators.has(indicator)) {
                result.put(indicator, indicators.getJSONObject(indicator)
                        .getJSONObject("basic")
                        .getString("displayValue"));
            } else {
                result.put(indicator, null);
            }
        }
        return result;
    }

    /**
     * Кеш кланов по игрокам, что бы по нескольку раз не дёргать сервисы
     */
    private Map<Long, Clan> playersClan = new HashMap<>();

    /**
     * Выдача сведений о событиях
     * @param playerId идентфикактор игрока
     * @param memberType тип участника
     */
    public List<Event> getEvents(long playerId, int memberType, long characterId, GameType gameType){

        class EventMember{
            private long playerId;
            private int memberType;
            private String playerName;
            private String teamName;
        }
        List<Event> result = new ArrayList<>();

        // Выборка всех событий
        int page = 0;
        while (true) {
            JSONObject obj = basicGet("/Destiny2/" + memberType + "/Account/"
                    + playerId + "/Character/" + characterId + "/Stats/Activities/?count=100&page=" + page++ + "&mode=" + gameType.paramName);
            if (obj == null) {
                break;
            }
            if (!obj.has("activities")) {
                break;
            }

            System.out.println(obj);
            JSONArray events = obj.getJSONArray("activities");
            // Теперь войдём в каждое событие
            for (int i = 0; i < events.length(); i++){
                Event event = new Event();
                event.setEventId(((JSONObject)events.get(i)).getJSONObject("activityDetails").getLong("instanceId"));

                try {
                    event.setEventDate(DATE_TIME_FORMAT.parse(((JSONObject)events.get(i)).getString("period").replaceAll("Z", "+0000")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Если указана дата начала и дата в отчёте больше(позже), то идём к следующему событию
                if (Settings.getInstance().getStartDate() != null && event.getEventDate().before(Settings.getInstance().getStartDate())){
                    continue;
                }

                // Если указана дата окончания и дата в отчёте меньше(раньше), то считаем, что дальше данные нам не нужны и выходим из цикла
                if (Settings.getInstance().getEndDate() != null && event.getEventDate().after(Settings.getInstance().getEndDate())){
                    break;
                }

                // Если событие уже обрабатывалось, то не будем больше его обрабатывать
                if (loadedEvents.contains(event.getEventId())){
                    continue;
                }
                loadedEvents.add(event.getEventId());

                // Достанем идентфикаторы участников и приндалежность к командам
                JSONArray eventMembersSrc = basicGet("/Destiny2/Stats/PostGameCarnageReport/" + event.getEventId() + "/").getJSONArray("entries");
                List<EventMember> eventMembers = new ArrayList<>();
                String playerTeam = null;
                for (Object eventMember : eventMembersSrc) {
                    EventMember ev = new EventMember();
                    ev.playerId = ((JSONObject)eventMember)
                            .getJSONObject("player")
                            .getJSONObject("destinyUserInfo")
                            .getLong("membershipId");
                    ev.playerName = ((JSONObject)eventMember)
                            .getJSONObject("player")
                            .getJSONObject("destinyUserInfo")
                            .getString("displayName");
                    if (((JSONObject)eventMember).getJSONObject("values").has("team")) {
                        ev.teamName = ((JSONObject) eventMember)
                                .getJSONObject("values")
                                .getJSONObject("team")
                                .getJSONObject("basic")
                                .getString("displayValue");
                    }
                    ev.memberType = ((JSONObject)eventMember)
                            .getJSONObject("player")
                            .getJSONObject("destinyUserInfo")
                            .getInt("membershipType");
                    eventMembers.add(ev);
                    // Запишем название команды, в которой учавствовал игрок, по которому ищем команды
                    if (ev.playerId == playerId){
                        playerTeam = ev.teamName;
                    }
                }
                // Разделим участников на команды
                for (EventMember member : eventMembers) {
                    Clan playerClan;
                    if (playersClan.containsKey(member.playerId)){
                        playerClan = playersClan.get(member.playerId);
                    }else {
                        playerClan = getClanByMember(member.playerId, member.memberType);
                        playersClan.put(member.playerId, playerClan);
                    }
                    Player player = new Player();
                    player.setId(member.playerId);
                    player.setMembershipType(member.memberType);
                    player.setName(member.playerName);
                    player.setClan(playerClan);
                    if (playerTeam == null || playerTeam.equals(member.teamName)){
                        event.getTeammates().add(player);
                    }else{
                        event.getEnemies().add(player);
                    }
                }
                result.add(event);
            }
        }
        return result;
    }
}