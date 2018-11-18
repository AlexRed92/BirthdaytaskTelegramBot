package com.alexred.telegrambot.bdquest;

import com.alexred.telegrambot.bdquest.service.GameController;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.alexred.telegrambot.bdquest.service.MessageController;
import com.alexred.telegrambot.bdquest.service.ScenarioLoader;
import com.alexred.telegrambot.bdquest.service.UserController;

import java.util.List;

public class Main {
    private static TelegramBot bot;
    private static MessageController messageController;
    private static UserController userController;
    private static GameController gameController;
    private static String token = "623329685:AAHNbYahZi2QbOjzxR286bwjmu1zJdZW1fE";
    private static String scenarioLocation = "src/main/resources/scenario.json";

    public static void main(String[] args) {

        init();

        ScenarioLoader scenarioLoader = new ScenarioLoader();
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (int z = 0; z < list.size(); z++) {
                    Message message = list.get(z).message();
                    messageController.processMessage(message);

                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    private static void init () {
        bot = new TelegramBot(token);
        userController = new UserController();
        gameController = new GameController(userController, scenarioLocation);
        messageController = new MessageController(userController, gameController, bot);
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static void setGameController(GameController gameController) {
        Main.gameController = gameController;
    }
}
