package com.example.demo.rest;

import com.example.demo.domain.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/chat")
public class ChatRestController {

    final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatRestController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> chat(Principal principal) {

        final String loggedUsername = principal.getName();

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ChatResponse> messages = chatMessageRepository
                .findAll(PageRequest.of(0, 20, sort))
                .stream()
                .map(m -> new ChatResponse(loggedUsername.equals(m.getSender()), m.getBody()))
                .collect(Collectors.toList());

        Collections.reverse(messages);

        return ResponseEntity.ok(messages);
    }
}
