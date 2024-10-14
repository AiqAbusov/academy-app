package com.aiq.academy.controller;

import com.aiq.academy.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpleMessageTemplate;


    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        System.out.println("Sending private message to: " + chatMessage.getRecipient());

        simpleMessageTemplate.convertAndSendToUser(
                chatMessage.getRecipient(),
                "/queue/messages",
                chatMessage
        );
    }

    @MessageMapping("/chat.addUser")
    public void addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
         headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

         System.out.println(chatMessage.getSender() + " has joined.");
    }
}
