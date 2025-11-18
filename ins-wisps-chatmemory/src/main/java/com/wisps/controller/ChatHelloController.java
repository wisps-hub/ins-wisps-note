package com.wisps.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/chat")
public class ChatHelloController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/once")
    public String once(@RequestParam("msg") String msg){
        return chatClient.prompt().user(msg).call().content();
    }

    @GetMapping("/memory/stream")
    public Flux<String> stream(@RequestParam("msg") String msg,
                               @RequestParam("uid")String uid){
        return chatClient.prompt(msg)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID , uid))
                .stream().content();
    }

}