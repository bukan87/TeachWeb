package model;

/**
 * @author by Ilin_ai on 22.09.2017.
 */
public enum IndicatorType {
    activitiesEntered("activitiesEntered", "Количество активностей"),
    activitiesWon("activitiesWon", "Пройдено успешно"), // матчей выиграно
    kills("kills", "убито"), // убито
    secondsPlayed("secondsPlayed", "Сыграно времени"), // - сыграно времени. Пример 2m 32s
    deaths("deaths", "Смертей"),
    averageLifespan("averageLifespan", "Средняя продолжительность жизни"), // - средняя продолжительность жизни. Пример 2m 32s
    bestSingleGameKills("bestSingleGameKills", "Максимальное кол-во убийств"), // - максимальное кол-во убийств за один матч
    bestSingleGameScore("bestSingleGameScore", "Максимальное кол-во очков"), // - максимальное кол-во очков
    kd("killsDeathsRatio", "К/Д"), // - К/Д
    kda("killsDeathsAssists", "КДА"), // - КДА
    headshots("precisionKills", "Хэдшотов"), // - хэдшоты
    weaponBestType("weaponBestType", "Любимое оружие"), // - любимое оружие
    longestKillSpree("longestKillSpree", "Самая длинная серия убийств"); // - самая длинная серия убийств;

    public String paramName;
    public String russianName;

    IndicatorType(String paramName, String russianName) {
        this.paramName = paramName;
        this.russianName = russianName;
    }
}
