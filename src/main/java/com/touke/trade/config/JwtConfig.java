package com.touke.trade.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("jwt")
@Data
public class JwtConfig {

    private String jwtSecret;

}
