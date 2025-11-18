package com.wisps.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.image.ImageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImgModelConfig {
    @Value("${spring.ai.dashscope.api-key}")
    private String ak;
    @Value("${wisps.t2i}")
    private String model;


    @Bean
    public ImageModel imageModel(){
        return DashScopeImageModel.builder()
                .dashScopeApi(new DashScopeImageApi(ak))
                .defaultOptions(DashScopeImageOptions.builder().withModel(model).build())
                .build();
    }

}
