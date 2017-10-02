package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author by Ilin_ai on 25.09.2017.
 */
public class Event {

    private long eventId;
    private Date eventDate;
    private List<Player> teammates;
    private List<Player> enemies;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public List<Player> getTeammates() {
        if (teammates == null)
            teammates = new ArrayList<>();
        return teammates;
    }

    public List<Player> getEnemies() {
        if (enemies == null)
            enemies = new ArrayList<>();
        return enemies;
    }
}
