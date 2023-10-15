package com.example.mongchi_shop.controller.product;

import com.example.mongchi_shop.dto.ProductDTO;
import com.example.mongchi_shop.service.ProductService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@WebServlet("/products")
public class ProductListController extends HttpServlet {
    static final int ROW_PER_PAGE = 8; // 페이지당 게시물 숫자
    private final ProductService PRODUCT_SERVICE = ProductService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        log.info("product list(GET)...");

        int currentPage = 1; // 페이지 번호의 기본 값
        int limit = ROW_PER_PAGE; // 페이지당 게시물 수
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }

        try {
            int totalRecord = PRODUCT_SERVICE.getAllProductCount(); // 전체 게시물 수
            List<ProductDTO> productDTOList = PRODUCT_SERVICE.getAllProduct(currentPage, limit);

            int totalPage; // 전체 페이지 계산
            if (totalRecord % limit == 0) {
                totalPage = totalRecord / limit;
                Math.floor(totalPage);
            }
            else {
                totalPage = totalRecord / limit;
                Math.floor(totalPage);
                totalPage = totalPage + 1;
            }

            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("productDTOList", productDTOList);
            req.getRequestDispatcher("/WEB-INF/product/list.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("list error");
        }
    }
}
