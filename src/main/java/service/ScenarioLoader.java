package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.GameTask;
import entity.Scenario;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScenarioLoader {

    private static final String WELCOME_MSG_PROP = "welcomeMsg";
    private static final String TASKS_PROP = "tasks";
    private static final String FIRST_TASK_PROP = "firstTask";
    private static final String ID_PROP = "id";
    private static final String INIT_MSG_PROP = "initMsg";
    private static final String EXPECTED_RESULT_PROP = "expectedResult";
    private static final String FINAL_MSG_PROP = "finalMsg";
    private static final String HELP_MSG_PROP = "helpMsg";
    private static final String CHEERUP_PROP = "cheerUp";
    private static final String DEPRESS_PROP = "depress";
    private static final String TEXT_PROP = "text";
    private static final String STICKERS_PROP = "stickers";
    private static final String AUDIO_PROP = "audio";

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
        final Scenario scenario = new Scenario();
        scenario.setWelcomeMsg(jsonObject.get(WELCOME_MSG_PROP).getAsString());
        scenario.setFirstTaskId(jsonObject.get(FIRST_TASK_PROP).getAsString());
        JsonArray tasks = jsonObject.getAsJsonArray(TASKS_PROP);
        tasks.forEach((i) -> {
            JsonObject task = i.getAsJsonObject();
            if (task != null && task instanceof JsonObject) {
                scenario.addTask(getTaskFromJson(task));
            }
        });

        return scenario;
    }

    private GameTask getTaskFromJson (JsonObject jsonObject) {
        String id = getJsonStringField(jsonObject, ID_PROP);
        String initMsg = getJsonStringField(jsonObject, INIT_MSG_PROP);
        String expectedResult = getJsonStringField(jsonObject, EXPECTED_RESULT_PROP);
        String finalMsg = getJsonStringField(jsonObject, FINAL_MSG_PROP);
        String helpMsg = getJsonStringField(jsonObject, HELP_MSG_PROP);
        return new GameTask(id, initMsg, expectedResult, finalMsg, helpMsg);
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
