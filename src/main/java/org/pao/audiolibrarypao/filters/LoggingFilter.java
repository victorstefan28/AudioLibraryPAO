package org.pao.audiolibrarypao.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.services.JwtService;
import org.pao.audiolibrarypao.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Optional;

@Component
public class LoggingFilter implements Filter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoggingService loggingService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        chain.doFilter(httpRequest, response);

        logRequestDetails(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }

    private void logRequestDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        if(!requestURI.contains("/api/") || requestURI.contains("/api/auth"))
            return;
        String user = extractUserFromJWT(request);
        int responseCode = response.getStatus();
        String payload = request.getQueryString();
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        saveLogToDatabase(requestURI, user, responseCode, body, payload);
    }

    private String extractUserFromJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            return parseJWT(token);
        }
        return "Anonymous";
    }

    private String parseJWT(String token) {

        Optional<User> user = jwtService.extractUser(token);
        if(user.isEmpty()){
            return "Anonymous";
        }
        else {
            return user.get().getEmail();
        }
    }

    private void saveLogToDatabase(String requestURI, String user, int responseCode, String body, String payload) {
        loggingService.logRequest(requestURI, user, responseCode, body, payload);
    }
}
