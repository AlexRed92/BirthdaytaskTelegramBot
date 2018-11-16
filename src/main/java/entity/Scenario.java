package entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {

    private String welcomeMsg;
    private String firstTaskId;
    private Map<String, GameTask> tasksMap;
    List<String> cheerUpPhrases;

    public Scenario() {
        tasksMap = new HashMap<String, GameTask>();
        cheerUpPhrases = new ArrayList<>();
    }

    public void addTask (GameTask task) {
        if (task != null) {
            String id = task.getId();
            if (tasksMap.containsKey(id)){
                System.out.println("We already have task " + id + " so, it would be overridden by new one");
            }
            tasksMap.put(id, task);
        }
    }

    public GameTask getTask (String taskId) {
        if (StringUtils.isNotBlank(taskId)) {
            return tasksMap.get(taskId);
        }
        return null;
    }

    public GameTask getNextTask (GameTask currentTask) {
        if (currentTask != null) {
            String nextId = currentTask.getId();
            if (StringUtils.isNotBlank(nextId)) {
                return tasksMap.get(nextId);
            }
        }
        return null;
    }

    //TODO: create validation
    public boolean isValid () {
        return true;
    }

    public GameTask getNextTask (String currentId) {
        if (StringUtils.isNotBlank(currentId)) {
            return getNextTask(tasksMap.get(currentId));
        }
        return null;
    }

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }

    public String getFirstTaskId() {
        return firstTaskId;
    }

    public void setFirstTaskId(String firstTaskId) {
        this.firstTaskId = firstTaskId;
    }
}
