package com.alexred.telegrambot.bdquest.service;

import com.alexred.telegrambot.bdquest.entity.GameTask;
import com.alexred.telegrambot.bdquest.entity.GameUser;
import com.alexred.telegrambot.bdquest.entity.Scenario;
import com.pengrad.telegrambot.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    private Map<Integer, GameUser> users;

    public UserController() {
        loadUsers();
    }

    // TODO: add save and load saved user
    private void loadUsers () {
        users = new HashMap<>();
    }

    public boolean moveToNextTask (GameUser user) {
        boolean result = false;
        if (isUserValidForGame(user)) {
            Scenario scenario = user.getScenario();
            GameTask nextTask = scenario.getNextTask(user.getGameTask().getId());
            if (nextTask != null) {
                user.setGameTask(nextTask);
                result = true;
            } else {
                System.out.println("THIS IS THE LAST LEVEL");
            }
        } else {
            System.out.println("User is invalid");
        }
        return result;
    }

    /**
     * User already start the game and contains фд куйгшкув ашудвы
     * @param user
     * @return
     */
    public boolean isUserValidForGame (GameUser user) {
        return user != null && user.getTgUser() != null && user.getGameTask() != null && user.getScenario() != null;
    }

    public GameUser createUser (User tgUser) {
        GameUser gameUser = null;
        if (tgUser != null && !isUserExist(tgUser.id())) {
            gameUser = new GameUser(tgUser);
            users.put(tgUser.id(), gameUser);
        }
        return gameUser;
    }

    public GameUser createUser (User tgUser, Scenario scenario) {
        GameUser gameUser = null;
        if (tgUser != null && scenario != null) {
            gameUser = createUser(tgUser);
            gameUser.setScenario(scenario);
            gameUser.setGameTask(scenario.getFirstTask());
        }
        return gameUser;
    }

    public boolean isUserExist (Integer id) {
        if (id != null) {
            return users.containsKey(id);
        }
        return false;
    }

    public GameUser getUser (Integer id) {
        GameUser user = null;
        if (isUserExist(id)) {
            user = users.get(id);
        }
        return user;
    }

    public boolean updateUser (Integer id, GameUser user) {
        boolean result = false;
        if (isUserExist(id) && user != null) {
            users.put(id, user);
            result = true;
        }
        return result;
    }

    public void deleteUser (Integer id) {
        if (isUserExist(id)) {
            users.remove(id);
        }
    }

}
