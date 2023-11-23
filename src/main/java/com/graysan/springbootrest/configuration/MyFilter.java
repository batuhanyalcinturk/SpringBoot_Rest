package com.graysan.springbootrest.configuration;

import jakarta.servlet.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.err.println("filter calisti");
        chain.doFilter(request, response);
    }
}
