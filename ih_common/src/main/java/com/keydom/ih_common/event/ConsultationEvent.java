package com.keydom.ih_common.event;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @date 20/4/29 16:45
 * @des
 */
public class ConsultationEvent implements Serializable {
    private static final long serialVersionUID = -3388742471837359411L;
    private String teamId;
    private String roomName;
    private ArrayList<String> accounts;
    private String teamName;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<String> accounts) {
        this.accounts = accounts;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
