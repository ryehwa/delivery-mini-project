package com.lucky_vicky.delivery_project.global.config;

import com.lucky_vicky.delivery_project.global.interceptor.UserIdArgumentResolver;
import com.lucky_vicky.delivery_project.global.interceptor.UserIdInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserIdArgumentResolver userIdArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserIdInterceptor())
                .addPathPatterns("/api/v1/categories/**")
                .addPathPatterns("/api/v1/stores/**")
                .addPathPatterns("/api/v1/notices/**")
                .addPathPatterns("/api/v1/reports/**")
                .excludePathPatterns("/api/v1/stores/public/**")
                .excludePathPatterns("/api/v1/notices/public/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(userIdArgumentResolver);
    }
}
