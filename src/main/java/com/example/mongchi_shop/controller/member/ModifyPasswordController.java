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
import java.sql.SQLException;

@Log4j2
@WebServlet("/member/modifyPassword")
public class ModifyPasswordController extends HttpServlet {
    private final MemberService service=MemberService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = dto.getEmailId();
        req.setAttribute("emailId",emailId);

        req.getRequestDispatcher("/WEB-INF/member/modifyPassword.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");
        String password = req.getParameter("password");
        try {
            log.info(dto.getEmailId());
            service.modifyPassword(dto.getEmailId(),password);
            resp.sendRedirect("/");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
