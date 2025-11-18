package com.wisps.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatHelloController {
    @Resource
    private ChatClient dashscopeChatClient;

    @GetMapping("/once")
    public String once(@RequestParam("msg") String msg){
        return dashscopeChatClient.prompt().user(msg).call().content();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam("msg") String msg){
        return dashscopeChatClient.prompt().user(msg).stream().content();
    }
}
