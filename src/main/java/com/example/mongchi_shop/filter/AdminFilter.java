package com.example.mongchi_shop.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.LogRecord;

@Log4j2
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if(session.getAttribute("admin") == null){
            log.info("ADMIN NULL!!");
            resp.sendRedirect("/");
        }else {
            chain.doFilter(req, resp);
        }
    }
}
