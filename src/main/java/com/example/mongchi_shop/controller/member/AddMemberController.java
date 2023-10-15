package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

@WebServlet("/addMember")
@Log4j2
public class AddMemberController extends HttpServlet {
    private final MemberService service = MemberService.INSTANCE;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("add GET");

        request.getRequestDispatcher("WEB-INF/member/addMember.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        log.info("/addMember(POST)...");

        MemberDTO memberDTO = new MemberDTO();
        try {
            BeanUtils.populate(memberDTO, req.getParameterMap());
            log.info("memberDTO: " + memberDTO);
            service.insertMember(memberDTO);
            resp.sendRedirect("/");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("add error");
        }

    }
}
