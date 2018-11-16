import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import entity.Scenario;
import service.ScenarioLoader;

import java.util.List;

public class Main {
    private static TelegramBot bot;
    private static String token = "725807056:AAGnmU0SznQXbRz7l_3KeVnWCu1S0iZOqvw";
    private static String scenarioLocation = "src/main/resources/scenario.json";

    public static void main(String[] args) {


        bot = new TelegramBot(token);
        ScenarioLoader scenarioLoader = new ScenarioLoader();
        Scenario scenario = scenarioLoader.readScenario(scenarioLocation);
        GetUpdatesResponse getUpdatesResponse;
        SendMessage sendMessage = null;
        int j = 0;
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (int z = 0; z < list.size(); z++) {
                    Message message = list.get(z).message();
                    Chat chat = message.chat();
                    User user = message.from();

                    if (message.text() != null) {
                        System.out.println("New message: " + message.text() + " id: " + message.messageId() + " from " + chat);
                        bot.execute(new SendMessage(chat.id(), "хуе мое, это твое " + message.text()));
                    } else {
                        System.out.println("New message, no text " + message.sticker());
                        bot.execute(new SendSticker(chat.id(), "CAADAgADCwADw0b1D5ViMr0eQV1zAg"));
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
