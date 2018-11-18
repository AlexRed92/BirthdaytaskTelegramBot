package com.alexred.telegrambot.bdquest.service;

import com.alexred.telegrambot.bdquest.entity.GameUser;
import com.alexred.telegrambot.bdquest.entity.Scenario;
import com.alexred.telegrambot.bdquest.entity.msg.GameMessage;
import com.pengrad.telegrambot.model.User;
import org.apache.commons.lang3.StringUtils;

public class GameController {

    private ScenarioLoader scenarioLoader;
    private String scenarioLocation;
    private UserController userController;

    public GameController(UserController userController, String scenarioLocation) {
        this.userController = userController;
        this.scenarioLocation = scenarioLocation;
        scenarioLoader = new ScenarioLoader();
    }

    public GameMessage startGame (User user) {
        GameMessage message;
        if (user != null) {
            Scenario scenario = scenarioLoader.readScenario(scenarioLocation);
            userController.createUser(user, scenario);
            message = createStandardMessage(scenario.getWelcomeMsg());
        } else {
            message = createFailMessage();
            System.out.println("GameController startGame: User is null");
        }
        return message;
    }

    public GameMessage checkTask (GameUser user, String answer) {
        GameMessage message = null;
        if (userController.isUserValidForGame(user) && StringUtils.isNotBlank(answer)) {
            if (StringUtils.equalsIgnoreCase(user.getGameTask().getExpectedResult(), answer)) {
                boolean isHaveNextTask = userController.moveToNextTask(user);
                if (isHaveNextTask) {
                    String initText = user.getGameTask().getInitMsg();
                    message = createStandardMessage(initText);
                } else {
                    message = endGame(user);
                }
            } else {
                message = createFailMessage();
            }
        }
        return message;
    }

    public GameMessage helpUser (GameUser user) {
        GameMessage help = null;
        if (userController.isUserValidForGame(user)) {
            help = createHelpMessage(user.getGameTask().getHelpMsg());
        }
        return help;
    }

    public GameMessage endGame (GameUser user) {
        return createStandardMessage("GAME OVER");
    }

    private GameMessage createStandardMessage(String text) {
        return new GameMessage(text, GameMessage.MsgType.STANDARD_MSG);
    }

    private GameMessage createHelpMessage (String text) {
        return new GameMessage(text, GameMessage.MsgType.HELP_MSG);
    }

    /**
     * we suppose to add text of fail message in the MessageController
     * @return
     */
    private GameMessage createFailMessage () {
        return new GameMessage("", GameMessage.MsgType.FAIL_MSG);
    }


    public ScenarioLoader getScenarioLoader() {
        return scenarioLoader;
    }



    public void setScenarioLoader(ScenarioLoader scenarioLoader) {
        this.scenarioLoader = scenarioLoader;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public String getScenarioLocation() {
        return scenarioLocation;
    }

    public void setScenarioLocation(String scenarioLocation) {
        this.scenarioLocation = scenarioLocation;
    }
}
