package com.example.demo.web;

import com.example.demo.domain.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

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
        model.addAttribute("user", loggedUsername);
        return "chat";
    }
}
