package com.alexred.telegrambot.bdquest.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Scenario {

    private String welcomeMsg;
    private GameTask firstTask;
    private Map<String, GameTask> tasksMap;
    private List<String> depressStickers;
    private List<String> depressPhrases;

    public Scenario() {
        tasksMap = new HashMap<>();
        depressStickers = new ArrayList<>();
        depressPhrases = new ArrayList<>();
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
            GameTask next = tasksMap.get(currentTask.getNextTaskId());
            if (next != null) {
                return next;
            }
        }
        return null;
    }

    public GameTask getNextTask (String currentId) {
        if (StringUtils.isNotBlank(currentId)) {
            return getNextTask(tasksMap.get(currentId));
        }
        return null;
    }

    // TODO: create validation
    public boolean isValid () {
        return true;
    }

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void setWelcomeMsg(String welcomeMsg) {
        this.welcomeMsg = welcomeMsg;
    }

    public List<String> getDepressStickers() {
        return depressStickers;
    }

    public void setDepressStickers(List<String> depressStickers) {
        this.depressStickers = depressStickers;
    }

    public List<String> getDepressPhrases() {
        return depressPhrases;
    }

    public void setDepressPhrases(List<String> depressPhrases) {
        this.depressPhrases = depressPhrases;
    }

    public GameTask getFirstTask() {
        return firstTask;
    }

    public void setFirstTask(GameTask firstTask) {
        this.firstTask = firstTask;
    }
}
