package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;
    private String sender;

    public ChatMessage() {
    }

    public ChatMessage(String body, String sender) {
        this.body = body;
        this.sender = sender;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }
}
