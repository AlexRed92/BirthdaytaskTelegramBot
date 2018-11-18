package com.alexred.telegrambot.bdquest.entity;

import com.pengrad.telegrambot.model.User;

public class GameUser {
    private User tgUser;
    private GameTask gameTask;
    private boolean isAdmin;
    private Scenario scenario;

    public GameUser() {
    }

    public GameUser(User tgUser) {
        this.tgUser = tgUser;
        gameTask = null;
        isAdmin = false;
        scenario = null;
    }

    public String getUsername () {
        return getTgUser().username();
    }

    public User getTgUser() {
        return tgUser;
    }

    public void setTgUser(User tgUser) {
        this.tgUser = tgUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public GameTask getGameTask() {
        return gameTask;
    }

    public void setGameTask(GameTask gameTask) {
        this.gameTask = gameTask;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
