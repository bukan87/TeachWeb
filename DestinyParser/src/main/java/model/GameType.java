package model;

/**
 * @author by Ilin_ai on 22.09.2017.
 */
public enum GameType {
    PVP("AllPvP", "allPvP", "PvP"),
    //IRON_BANNER("IronBanner", "ironBanner"), пока не работает
    TRAILS_OF_NINE("TrialsOfTheNine", "trialsofthenine", "TrialsOfTheNine"),
    PVE("AllPvE", "allPvE", "PvE"),
    STRIKE("Strike", "strike", "Strike"),
    RAID("Raid", "raid", "Raid"),
    PATROL("Patrol", "patrol", "Patrol"),
    STORY("Story", "story", "Story"),
    NIGHTFALL("Nightfall", "nightfall", "Nightfall"),
    HEROIC_NIGHTFALL("HeroicNightfall", "heroicNightfall", "HeroicNightfall");

    public String paramName;
    public String keyName;
    public String settingsSheetName;

    GameType(String paramName, String keyName, String settingsSheetName) {
        this.paramName = paramName;
        this.keyName = keyName;
        this.settingsSheetName = settingsSheetName;
    }
}
