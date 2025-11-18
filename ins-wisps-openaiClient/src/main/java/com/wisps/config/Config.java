package com.wisps.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ChatClient openAiChatClient(ChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel).build();
    }
}