package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Ilin_ai on 21.09.2017.
 * Сущность игрок
 */
public class Player {
    private long id;
    private long clanId;
    private String name;
    private int membershipType;
    private List<Character> characters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClanId() {
        return clanId;
    }

    public void setClanId(long clanId) {
        this.clanId = clanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(int membershipType) {
        this.membershipType = membershipType;
    }

    public List<Character> getCharacters() {
        if (characters == null)
            characters = new ArrayList<>();
        return characters;
    }
}