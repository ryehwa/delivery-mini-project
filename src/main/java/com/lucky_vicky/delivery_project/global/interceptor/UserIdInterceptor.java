package com.lucky_vicky.delivery_project.global.interceptor;

import com.lucky_vicky.delivery_project.global.exception.BusinessLogicException;
import com.lucky_vicky.delivery_project.global.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class UserIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication = {}", authentication);

        if (authentication == null) {
            throw new BusinessLogicException(ExceptionCode.EMPTY_AUTHENTICATION);
        }

        request.setAttribute("USER_ID", authentication.getName());
        log.info("USER_ID = {}", authentication.getName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

