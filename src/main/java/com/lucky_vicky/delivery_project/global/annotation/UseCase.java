package com.lucky_vicky.delivery_project.global.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface UseCase {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
