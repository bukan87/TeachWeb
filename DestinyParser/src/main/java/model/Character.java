package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class Character {
    private long id;
    private int light;
    private Clan clan;

    //private Map<GameType, Map<String, String>> gameIndicators;
    private Map<GameType, Map<String, Map<String, String>>> gameIndicators;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Map<GameType, Map<String, Map<String, String>>> getGameIndicators() {
        if (gameIndicators == null) {
            gameIndicators = new HashMap<>();
        }
        return gameIndicators;
    }
}
