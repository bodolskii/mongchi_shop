package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@Log4j2
@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Login GET");
        req.getRequestDispatcher("/WEB-INF/member/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Login POST");

        String emailId = req.getParameter("emailId");
        String password = req.getParameter("password");
        String auto = req.getParameter("auto");
        boolean rememberMe = auto != null && auto.equals("on"); // auto 체크하면 true

        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(emailId, password);
            if(memberDTO.getAdmin().equals("Y")) {
                log.info("YES ADMIN");
                HttpSession session = req.getSession();
                session.setAttribute("admin",memberDTO);
                resp.sendRedirect("/admin/products");
                return;
            }



            if(rememberMe) {
                // 로그인 성공해야 임의의 문자열 생성
                String uuid= UUID.randomUUID().toString(); // 임의의 문자열 생성

                MemberService.INSTANCE.modifyUuid(emailId, uuid);
                memberDTO.setUuid(uuid);

                Cookie rememberCookie = new Cookie("remember-me", uuid);
                rememberCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효기간 일주일로 설정
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }

            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/");
        } catch(Exception e) {
            resp.sendRedirect("/login?result=error");
        }
    }

}
