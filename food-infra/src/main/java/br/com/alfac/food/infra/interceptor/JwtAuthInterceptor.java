package br.com.alfac.food.infra.interceptor;

import br.com.alfac.food.infra.helper.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private static final String AUTHENTICATED = "AUTHENTICATED";
    private static final String ANONYMOUS = "ANONYMOUS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("auth");

        if (StringUtils.isBlank(token) || !token.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        } else if (AUTHENTICATED.equalsIgnoreCase(JwtHelper.getWho(token)) || ANONYMOUS.equalsIgnoreCase(JwtHelper.getWho(token)))
            return true;
        else {
            String a = JwtHelper.getWho(token);
            response.setStatus(403);
            return false;
        }

    }

}