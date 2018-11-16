package entity;

public class GameTask {

    private String id;
    private String initMsg;
    private String expectedResult;
    private String finalMsg;
    private String helpMsg;

    public GameTask() {
    }

    public GameTask(String id, String initMsg, String expectedResult, String finalMsg, String helpMsg) {
        this.id = id;
        this.initMsg = initMsg;
        this.expectedResult = expectedResult;
        this.finalMsg = finalMsg;
        this.helpMsg = helpMsg;
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

    public String getFinalMsg() {
        return finalMsg;
    }

    public void setFinalMsg(String finalMsg) {
        this.finalMsg = finalMsg;
    }

    public String getHelpMsg() {
        return helpMsg;
    }

    public void setHelpMsg(String helpMsg) {
        this.helpMsg = helpMsg;
    }
}
