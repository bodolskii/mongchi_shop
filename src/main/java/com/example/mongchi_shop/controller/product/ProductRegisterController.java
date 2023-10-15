package com.example.mongchi_shop.controller.product;

import com.example.mongchi_shop.dto.ProductDTO;
import com.example.mongchi_shop.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@Log4j2
@WebServlet("/admin/products/register")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, location = "c:/upload")
public class ProductRegisterController extends HttpServlet {
    private final ProductService PRODUCT_SERVICE = ProductService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/admin/products/register(GET)...");
        req.getRequestDispatcher("/WEB-INF/product/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/admin/products/register(POST)...");
        ProductDTO productDTO = new ProductDTO();
        try {
            // 이미지 파일 저장을 위해 request로 부터 Part 객체 참조.
            Part part = req.getPart("file");
            String fileName = PRODUCT_SERVICE.getFileName(part);
            log.info("fileName: " + fileName);
            if (fileName != null && !fileName.isEmpty()) {
                part.write(fileName); // 파일 이름이 있으면 파일 저장
            }

            BeanUtils.populate(productDTO, req.getParameterMap());
            log.info("productDTO: " + productDTO);

            // 이미지 파일 이름을 News 객체에 저장.
            productDTO.setFileName("/upload/product" + fileName);

            PRODUCT_SERVICE.registerProduct(productDTO);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("register error");
        }

        resp.sendRedirect("/admin/products");
    }

}
