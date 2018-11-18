package com.alexred.telegrambot.bdquest.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.alexred.telegrambot.bdquest.entity.GameTask;
import com.alexred.telegrambot.bdquest.entity.Scenario;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ScenarioLoader {

    private static final String WELCOME_MSG_PROP = "welcomeMsg";
    private static final String TASKS_PROP = "tasks";
    private static final String ID_PROP = "id";
    private static final String INIT_MSG_PROP = "initMsg";
    private static final String INIT_TASK_PROP = "initTask";
    private static final String EXPECTED_RESULT_PROP = "expectedResult";
    private static final String HELP_MSG_PROP = "helpMsg";
    private static final String NEXT_ID_PROP= "nextId";
    private static final String DEPRESS_TEXT_PROP = "depressText";
    private static final String DEPRESS_STICKERS_PROP = "depressStickers";

    public Scenario readScenario (String location) {
        Scenario scenario = null;
        if (StringUtils.isNotBlank(location)) {
            try {
                JsonParser parser = new JsonParser();
                Object scenarioObj = parser.parse(new FileReader(location));
                if (scenarioObj != null && scenarioObj instanceof JsonObject) {
                    scenario = fillScenarioFromJson((JsonObject) scenarioObj);
                    //TODO: validate scenario
                    System.out.println("Scenario is ready");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return scenario;
    }

    private Scenario fillScenarioFromJson (JsonObject jsonObject) {
        Scenario scenario = new Scenario();
        scenario.setWelcomeMsg(jsonObject.get(WELCOME_MSG_PROP).getAsString());
        JsonArray tasks = jsonObject.getAsJsonArray(TASKS_PROP);
        tasks.forEach((i) -> {
            JsonObject task = i.getAsJsonObject();
            if (task != null) {
                scenario.addTask(getTaskFromJson(task));
            }
        });
        JsonArray depressText = jsonObject.getAsJsonArray(DEPRESS_TEXT_PROP);
        JsonArray depressStickers = jsonObject.getAsJsonArray(DEPRESS_STICKERS_PROP);

        scenario.setDepressPhrases(getStringListFromJsonArray(depressText));
        scenario.setDepressStickers(getStringListFromJsonArray(depressStickers));
        scenario.setFirstTask(scenario.getTask(jsonObject.get(INIT_TASK_PROP).getAsString()));

        return scenario;
    }


    private GameTask getTaskFromJson (JsonObject jsonObject) {
        String id = getJsonStringField(jsonObject, ID_PROP);
        String initMsg = getJsonStringField(jsonObject, INIT_MSG_PROP);
        String expectedResult = getJsonStringField(jsonObject, EXPECTED_RESULT_PROP);
        String helpMsg = getJsonStringField(jsonObject, HELP_MSG_PROP);
        String nextId = getJsonStringField(jsonObject, NEXT_ID_PROP);
        return new GameTask(id, initMsg, expectedResult, helpMsg, nextId);
    }

    private List<String> getStringListFromJsonArray (JsonArray array) {
        List <String> list = new ArrayList<>();
        if (array != null) {
            array.forEach((i) -> {
                String val = i.getAsString();
                list.add(val);
            });
        }
        return list;
    }

    private String getJsonStringField (JsonObject jsonObject, String fieldName) {
        String value = null;
        if (jsonObject != null && StringUtils.isNotBlank(fieldName)) {
            JsonElement element = jsonObject.get(fieldName);
            if (element != null) {
                value = element.getAsString();
            }
        }
        return value;
    }
}
