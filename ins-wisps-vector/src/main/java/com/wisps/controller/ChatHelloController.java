package com.wisps.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatHelloController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/once")
    public String once(@RequestParam("msg") String msg){
        return chatClient.prompt().user(msg).call().content();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam("msg") String msg){
        return chatClient.prompt().user(msg).stream().content();
    }
}
