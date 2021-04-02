package com.tesco.repository;

import com.tesco.util.ResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class UrlQueryLoader {
    @Bean(name = "urlByValueQuery")
    public String urlByValueSQL(@Value("classpath:sql/url/urlByValue.sql")
        Resource resource) {
        return ResourceLoader.asString(resource);
    }

    @Bean(name = "urlByIdQuery")
    public String urlByIdSQL(@Value("classpath:sql/url/urlById.sql")
        Resource resource) {
        return ResourceLoader.asString(resource);
    }

    @Bean(name = "urlUpdate")
    public String urlUpdateSQL(@Value("classpath:sql/url/urlUpdate.sql")
        Resource resource) {
        return ResourceLoader.asString(resource);
    }
}
