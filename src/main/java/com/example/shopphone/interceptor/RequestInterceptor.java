package com.example.shopphone.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Lấy username + role từ session nếu có
        HttpSession session = request.getSession(false);
        if (session != null) {
            request.setAttribute("currentUsername", session.getAttribute("username"));
            request.setAttribute("currentRole", session.getAttribute("role"));
        } else {
            request.setAttribute("currentUsername", null);
            request.setAttribute("currentRole", null);
        }

        // Lấy path hiện tại để highlight menu
        String currentPath = request.getRequestURI();
        request.setAttribute("currentPath", currentPath);

        return true;
    }
}
