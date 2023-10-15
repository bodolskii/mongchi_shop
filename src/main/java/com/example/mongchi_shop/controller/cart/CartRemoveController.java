package com.example.mongchi_shop.controller.cart;

import com.example.mongchi_shop.service.CartService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet("/cart/remove")
public class CartRemoveController extends HttpServlet {
    private final CartService CART_SERVICE = CartService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/cart/remove(GET)...");
        String[] checks = req.getParameterValues("checks");
        int[] cnos = new int[checks.length];

        for (int i = 0; i < checks.length; i++) {
            cnos[i] = Integer.parseInt(checks[i]);
        }

        try {
            CART_SERVICE.removeCart(cnos);
            resp.sendRedirect("/cart/list");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("remove error");
        }
    }
}
