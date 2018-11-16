package entity;

import com.pengrad.telegrambot.model.User;

public class GameUser {
    private User tgUser;
    private String lastLevel;

    public User getTgUser() {
        return tgUser;
    }

    public void setTgUser(User tgUser) {
        this.tgUser = tgUser;
    }

    public String getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(String lastLevel) {
        this.lastLevel = lastLevel;
    }
}
