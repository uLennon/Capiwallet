package com.digital.wallet.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@SuppressWarnings("deprecation")
public class ErroConfig implements ErrorController, AuthenticationFailureHandler {

    @RequestMapping("/error")
    public ModelAndView erroGeral(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.parseInt(status.toString());

        if(statusCode == HttpStatus.NOT_FOUND.value()){
            return new ModelAndView("erro");
        }else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            return new ModelAndView("erro");
        }
        return new ModelAndView("erro");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        request.getSession().setAttribute("error", "Usuário ou senha incorretos");
        response.sendRedirect("/login?error=true");
    }
}
