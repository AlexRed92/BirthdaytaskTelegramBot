package com.alexred.telegrambot.bdquest.service;

import com.alexred.telegrambot.bdquest.entity.GameUser;
import com.alexred.telegrambot.bdquest.entity.Scenario;
import com.alexred.telegrambot.bdquest.entity.msg.GameAudioMessage;
import com.alexred.telegrambot.bdquest.entity.msg.GameMessage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendSticker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

public class MessageController {
    private UserController userController;
    private GameController gameController;
    private TelegramBot bot;
    private Random random;

    private final static String RESTART_CMD = "/restart";
    private final static String HELP_CMD = "/help";
    private final static String ADMIN_CMD = "/admin";
    private final static String COMMAND_DEMARCATOR = "/";

    public MessageController(UserController userController, GameController gameController, TelegramBot bot) {
        this.userController = userController;
        this.gameController = gameController;
        this.bot = bot;
        random = new Random();
    }

    public void processMessage (Message message) {
        BaseRequest requst = null;
        if (message != null) {
            Chat chat = message.chat();
            User user = message.from();
            String text = message.text();
            System.out.println(message);
            if(chat != null && user != null) {
                GameUser gameUser = userController.getUser(user.id());
                GameMessage gameMessage = null;
                // new user
                if (gameUser == null) {
                    gameMessage = gameController.startGame(user);
                } else if (StringUtils.isBlank(text)) {
                    // user send some shit instead of text
                    System.out.println(message.sticker());
                    gameMessage = new GameMessage("", GameMessage.MsgType.FAIL_MSG);
                } else if (isCommand(text)) {
                    // user send a command /help or /admin
                    if (text.contains(HELP_CMD)) {
                        gameMessage = gameController.helpUser(gameUser);
                    } else if (text.contains(RESTART_CMD)) {
                        userController.deleteUser(user.id());
                        gameMessage = new GameMessage("", GameMessage.MsgType.STANDARD_MSG);
                    } else {
                        gameMessage = new GameMessage("", GameMessage.MsgType.FAIL_MSG);
                    }
                } else {
                    // user answer for question
                    gameMessage = gameController.checkTask(gameUser, text);
                }
                sendMessage(gameMessage, gameUser, chat);
            }
        }
    }

    private void sendMessage (GameAudioMessage message, Chat chat) {
//        bot.execute(new SendAudio());

    }

    private void sendMessage (GameMessage message, GameUser gameUser, Chat chat) {
        switch (message.getMsgType()) {
            case STANDARD_MSG:
                bot.execute(new SendMessage(chat.id(), message.getTextMessage()));
                break;
            case FAIL_MSG:
                sendRandomFailMsg(chat, gameUser);
                break;
            case HELP_MSG:
                sendHelpMsg(chat, gameUser, message.getTextMessage());
        }
    }

    private void sendRandomFailMsg (Chat chat, GameUser gameUser) {
        Scenario scenario = gameUser.getScenario();
        List<String> depressPhrases = scenario.getDepressPhrases();
        List<String> depressStickers = scenario.getDepressStickers();

        String depressText = depressPhrases.get(random.nextInt(depressPhrases.size()));
        String depressSticker = depressStickers.get(random.nextInt(depressStickers.size()));
        bot.execute(new SendMessage(chat.id(), depressText));
        bot.execute(new SendSticker(chat.id(), depressSticker));
    }

    private void sendHelpMsg (Chat chat, GameUser gameUser, String text) {
        if (StringUtils.isNotBlank(text)) {
            bot.execute(new SendMessage(chat.id(), text));
        } else {
            sendRandomFailMsg(chat, gameUser);
        }
    }

    private boolean isCommand (String text) {
        return StringUtils.isNotBlank(text) && text.startsWith(COMMAND_DEMARCATOR);
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
