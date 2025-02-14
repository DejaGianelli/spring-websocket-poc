package com.example.demo;

public class ChatMessageViewModel {
    private boolean me;
    private String body;

    public ChatMessageViewModel(boolean me, String body) {
        this.me = me;
        this.body = body;
    }

    public boolean isMe() {
        return me;
    }

    public String getBody() {
        return body;
    }
}
