package com.wisps.config;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${wisps.model}")
    private String model;

    @Autowired
    private RedisChatMemoryRepository redisChatMemoryRepository;

    @Bean
    public ChatClient chatClient(ChatModel chatModel){
        return ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder()
                        .chatMemoryRepository(redisChatMemoryRepository).maxMessages(10).build()).build())
                .defaultOptions(ChatOptions.builder().model(model).build())
                .build();
    }


}