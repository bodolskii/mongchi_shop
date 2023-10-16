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
@WebServlet("/member/modify")
public class ModifyController extends HttpServlet {
    private final MemberService service = MemberService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = req.getSession();
            MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");


            // 데이터 담기
            req.setAttribute("dto", dto);
            req.getRequestDispatcher("/WEB-INF/member/modify.jsp").forward(req,resp);
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new ServletException("read error");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");
        String emailId =  dto.getEmailId();
        MemberDTO memberDTO = MemberDTO.builder()
                .emailId(emailId)
                .phone(req.getParameter("phone"))
                .zipCode(req.getParameter("zipCode"))
                .address01(req.getParameter("address01"))
                .address02(req.getParameter("address02"))
                .address03(req.getParameter("address03"))
                .build();

        try {
            service.modifyMember(memberDTO);
            log.info( "CONTROLLER MODIFY ==" + memberDTO );
            resp.sendRedirect("/");
        } catch (Exception e) {
            throw new RuntimeException(e + "CONTROLLMODIFY ERROR!!!");
        }

    }
}
