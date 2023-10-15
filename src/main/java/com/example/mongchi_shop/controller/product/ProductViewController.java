package com.example.mongchi_shop.controller.product;

import com.example.mongchi_shop.dto.ProductDTO;
import com.example.mongchi_shop.service.ProductService;
import com.example.mongchi_shop.service.QnABoardService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@WebServlet("/products/product")
public class ProductViewController extends HttpServlet {
    private final ProductService PRODUCT_SERVICE = ProductService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        log.info("/products/product(GET)...");

        int pno = Integer.parseInt(req.getParameter("pno"));
        log.info("pno: " + pno);

        try {
            ProductDTO productDTO = PRODUCT_SERVICE.getProductByPno(pno);
            req.setAttribute("productDTO", productDTO);
            req.getRequestDispatcher("/WEB-INF/product/view.jsp").forward(req, resp);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("view error");
        }
    }
}
