package com.alexred.telegrambot.bdquest.entity.msg;

public class GameMessage {
    private String textMessage;
    private MsgType msgType;

    public enum MsgType {STANDARD_MSG, HELP_MSG, FAIL_MSG}

    public GameMessage() {

    }

    public GameMessage(String textMessage, MsgType msgType) {
        this.textMessage = textMessage;
        this.msgType = msgType;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
