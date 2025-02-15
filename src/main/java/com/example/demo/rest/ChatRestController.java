package com.example.demo.rest;

import com.example.demo.domain.ChatMessage;
import com.example.demo.domain.GetMessageChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/chat")
public class ChatRestController {

    final GetMessageChunkService getMessageChunkService;

    @Autowired
    public ChatRestController(GetMessageChunkService getMessageChunkService) {
        this.getMessageChunkService = getMessageChunkService;
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> chat(@RequestParam(name = "lrid", required = false) final Integer lastReadId,
                                                   final Principal principal) {

        MultiValueMap<String, String> headers = new HttpHeaders();

        List<ChatMessage> messages = getMessageChunkService.execute(lastReadId);

        if (!messages.isEmpty()) {
            headers.put("lrid", List.of(messages.getFirst().getId().toString()));
        }

        List<ChatResponse> response = messages.stream()
                .map(cm -> createChatResponse(cm, principal))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, headers, 200);
    }

    private ChatResponse createChatResponse(ChatMessage message, Principal principal) {
        boolean me = message.getSender().equals(principal.getName());
        return new ChatResponse(message.getId(), me, message.getBody());
    }
}
