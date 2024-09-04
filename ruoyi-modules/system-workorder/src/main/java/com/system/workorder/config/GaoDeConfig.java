package com.system.workorder.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
@Component
public class GaoDeConfig {
    @Value("${gaode.mapKey}")
    String mapKey;
}
