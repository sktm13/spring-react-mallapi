package org.yujin.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    // @Override
    // public void addFormatters(FormatterRegistry registry) {
    //     registry.addFormatter(new LocalDateFormatter());
    // }

    // CORS설정 추가
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {

    // registry.addMapping("/**")
    // .allowedOrigins("*") // 이부분으로 조정
    // .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
    // .maxAge(500)
    // .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    // }

}
