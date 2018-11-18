package com.alexred.telegrambot.bdquest.entity;

public class GameTask {

    private String id;
    private String initMsg;
    private String expectedResult;
    private String helpMsg;
    private String nextTaskId;

    public GameTask() {
    }

    public GameTask(String id, String initMsg, String expectedResult, String helpMsg, String nextTaskId) {
        this.id = id;
        this.initMsg = initMsg;
        this.expectedResult = expectedResult;
        this.helpMsg = helpMsg;
        this.nextTaskId = nextTaskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitMsg() {
        return initMsg;
    }

    public void setInitMsg(String initMsg) {
        this.initMsg = initMsg;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getHelpMsg() {
        return helpMsg;
    }

    public void setHelpMsg(String helpMsg) {
        this.helpMsg = helpMsg;
    }

    public String getNextTaskId() {
        return nextTaskId;
    }

    public void setNextTaskId(String nextTaskId) {
        this.nextTaskId = nextTaskId;
    }
}
