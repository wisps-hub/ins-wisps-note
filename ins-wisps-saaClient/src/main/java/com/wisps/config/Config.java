package com.wisps.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${spring.ai.dashscope.api-key}")
    private String ak;
    @Value("${spring.ai.dashscope.chat.options.model}")
    private String model;

    @Bean
    public ChatClient dashscopeChatClient(ChatModel chatModel){
        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder().model(model).build())
                .build();
    }

}