package com.example.mongchi_shop.filter;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebFilter(urlPatterns = {"/qnaBoards/*, /products/*, /members/*, /cart, /reviews/*"})
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Login Check Filter");
        request.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        log.info("session loginInfo: " + session.getAttribute("loginInfo"));

        // 로그인이 된 경우 종료되도록
        if (session.getAttribute("loginInfo") != null) {
            chain.doFilter(request, response);
            return;
        }

        // session에 loginInfo 값이 없는 경우, 쿠키값 확인해 자동로그인 처리
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // session에 loginInfo 값이 없고, 쿠키값도 없다면 로그인으로 이동
        if (cookie == null) {
            resp.sendRedirect("/login");
        }
        else { // 쿠키 존재하는 상황
            log.info("cookie는 존재하는 상황");
            String uuid = cookie.getValue();

            try { // uuid에 해당하는 데이터 찾아서 session에 loginInfo 생성
                // 데이터베이스 확인
                MemberDTO memberDTO = MemberService.INSTANCE.getByUuid(uuid);
                session.setAttribute("loginInfo", memberDTO);

                log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO);

                if (memberDTO == null) {
                    throw new Exception("Cookie value is not valid");
                } else { // 정보를 session에 추가
                    session.setAttribute("loginInfo", memberDTO);
                    chain.doFilter(req, resp);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                resp.sendRedirect("/login");
            }

        }

    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        // 매개변수로 받은 cookies에서 cookieName 일므을 가진 쿠키 찾아서 리턴. 없으면 null 반환
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                // 찾는 쿠키 있는 경우
                return cookie;
            }
        }
        return null;
    }
}
