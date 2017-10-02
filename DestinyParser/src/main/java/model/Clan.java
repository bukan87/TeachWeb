package model;

/**
 * @author by Ilin_ai on 25.09.2017.
 */
public class Clan {
    private long clanID;
    private String name;

    public Clan(long clanID, String name) {
        this.clanID = clanID;
        this.name = name;
    }

    public Clan(){}

    public long getClanID() {
        return clanID;
    }

    public void setClanID(long clanID) {
        this.clanID = clanID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
