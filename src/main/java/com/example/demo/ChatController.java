package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/chat")
    public String home(Model model, Principal principal) {

        final String loggedUsername = principal.getName();

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ChatMessageViewModel> messages = chatMessageRepository
                .findAll(PageRequest.of(0, 20, sort))
                .stream()
                .map(m -> new ChatMessageViewModel(loggedUsername.equals(m.getSender()), m.getBody()))
                .collect(Collectors.toList());

        Collections.reverse(messages);

        model.addAttribute("user", loggedUsername);
        model.addAttribute("messages", messages);

        return "chat";
    }
}
