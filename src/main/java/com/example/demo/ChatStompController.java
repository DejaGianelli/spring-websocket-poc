package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.nio.charset.Charset;
import java.security.Principal;

@Controller
public class ChatStompController {

    static Logger logger = LoggerFactory.getLogger(ChatStompController.class);

    final ObjectMapper objectMapper;
    final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatStompController(ChatMessageRepository chatMessageRepository, ObjectMapper objectMapper) {
        this.chatMessageRepository = chatMessageRepository;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/chat")
    public String handle(Message message, @Header("simpSessionId") String sessionId, Principal principal)
            throws JsonProcessingException {

        String userName = principal.getName();

        byte[] messageBytes = (byte[]) message.getPayload();
        String messageString = new String(messageBytes, Charset.defaultCharset());

        ChatMessageData chatMessageData = objectMapper.readValue(messageString, ChatMessageData.class);

        logger.info("Received message from: {}. Session id: {}. Message: {}",
                chatMessageData.sender(), sessionId, chatMessageData.body());

        //TODO: add FK to user table instead of sender name
        chatMessageRepository.save(new ChatMessage(chatMessageData.body, chatMessageData.sender));

        chatMessageData = new ChatMessageData(userName, chatMessageData.body());

        return objectMapper.writeValueAsString(chatMessageData);
    }

    public record ChatMessageData(String sender, String body) {
    }
}