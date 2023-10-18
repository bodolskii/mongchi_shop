package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.dto.OrderDTO;
import com.example.mongchi_shop.service.MemberService;
import com.example.mongchi_shop.service.OrderService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@WebServlet("/member/myorder")
public class MyoderController extends HttpServlet {
    private final OrderService orderService = OrderService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = dto.getEmailId();
        try {
            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList = orderService.getOrderByEmailId(emailId);
            req.setAttribute("orderDTOList",orderDTOList);
            log.info("MYORDERCONTROLLER");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/WEB-INF/member/myorder.jsp").forward(req,resp);
    }
}
