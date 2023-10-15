package com.example.mongchi_shop.controller.cart;

import com.example.mongchi_shop.dto.CartDTO;
import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebServlet("/cart/add")
public class CartAddController extends HttpServlet {
    private final CartService CART_SERVICE = CartService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/cart/add(POST)...");

        HttpSession session = req.getSession();
        String orderId = (String) session.getAttribute("orderId");
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = memberDTO.getEmailId();

        int pno = Integer.parseInt(req.getParameter("pno"));
        log.info("pno: " + pno);

        CartDTO cartDTO = new CartDTO();
        try {
            BeanUtils.populate(cartDTO, req.getParameterMap());
            cartDTO.setOrderId(orderId);
            cartDTO.setEmailId(emailId);
            log.info("cartDTO: " + cartDTO);

            CART_SERVICE.addCart(cartDTO);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("add error");
        }

        resp.sendRedirect("/products/product?pno="+pno);
    }
}
