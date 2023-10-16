package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebServlet("/member/removeMember")
public class RemoveMember extends HttpServlet {
    private final MemberService service = MemberService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");
        log.info(dto.getEmailId());
        try {
            service.removeMember(dto.getEmailId());
            resp.sendRedirect("/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
