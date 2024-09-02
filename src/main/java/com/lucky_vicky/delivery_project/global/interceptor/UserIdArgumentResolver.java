package com.lucky_vicky.delivery_project.global.interceptor;

import com.lucky_vicky.delivery_project.global.annotation.UserId;
import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
@Slf4j
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UUID.class) && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        final Object userIdObj = webRequest.getAttribute("USER_ID", NativeWebRequest.SCOPE_REQUEST);

        log.info("resolveArgument = {}", userIdObj);
        if (userIdObj == null) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED_ERROR);
        }

        return UUID.fromString(userIdObj.toString());
    }
}
