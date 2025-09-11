package com.wisps.zdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wisps")
public class ServerProperties {
    private String serverUrl;
    private String serverBackupUrl;
}
