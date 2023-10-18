package com.example.mongchi_shop.controller.cart;

import com.example.mongchi_shop.dto.CartDTO;
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
@WebServlet("/cart/list")
public class CartListController extends HttpServlet {
    private final CartService CART_SERVICE = CartService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/cart(GET)...");

        // 주문번호 사용
        HttpSession session = req.getSession();
        String orderId = (String) session.getAttribute("orderId");

        try {
            List<CartDTO> cartDTOList = CART_SERVICE.getCartByOrderId(orderId);
            log.info("cartDTOList: " + cartDTOList);

            session.setAttribute("cartDTOList", cartDTOList);
            req.getRequestDispatcher("/WEB-INF/cart/cart.jsp").forward(req, resp);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("list error");
        }
    }
}
