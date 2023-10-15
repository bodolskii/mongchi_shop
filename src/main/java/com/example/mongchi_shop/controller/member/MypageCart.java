package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.CartDTO;
import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.CartService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@Log4j2
@WebServlet("/members/mycart")
public class MypageCart extends HttpServlet {

    private final CartService CART_SERVICE = CartService.INSTANCE;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String orderId = (String) session.getAttribute("orderId");
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = memberDTO.getEmailId();

        try {
            List<CartDTO> cartDTOList = CART_SERVICE.getCartByOrderId(orderId);
            log.info("cartDTOList: " + cartDTOList);

            session.setAttribute("cartDTOList", cartDTOList);
            req.getRequestDispatcher("/WEB-INF/member/mycart.jsp").forward(req, resp);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("list error");
        }
    }
}
