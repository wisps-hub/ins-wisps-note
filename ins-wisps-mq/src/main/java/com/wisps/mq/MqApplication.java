package com.wisps.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Arrays;

@SpringBootApplication
public class MqApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MqApplication.class, args);

        // 获取并打印所有Bean的名称和类型
        System.out.println("=== Spring容器中的所有Bean对象 ===");
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames); // 排序便于查看

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            System.out.println("Bean名称: " + beanName + ", 类型: " + bean.getClass().getSimpleName());
        }

        System.out.println("\n总共找到 " + beanNames.length + " 个Bean对象");
    }

}