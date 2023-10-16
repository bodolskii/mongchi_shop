package com.example.mongchi_shop.controller.review;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.dto.ReviewDTO;
import com.example.mongchi_shop.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Log4j2
@WebServlet ("/review/add")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, location = "c:/upload/review")
public class ReviewAddController extends HttpServlet {
    private final ReviewService reviewService = ReviewService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/add doGet()...");

        req.getRequestDispatcher("/WEB-INF/review/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/add doPost()...");
        ReviewDTO reviewDTO = new ReviewDTO();

        int pno = Integer.parseInt(req.getParameter("pno"));
        /* pno 값을 불러옴 */

        try {
            Part part = req.getPart("fileName");
            String fileName = reviewService.getFileName(part);
            log.info("part : " + part);
            log.info("fileName : " + fileName);
            if (fileName != null && !fileName.isEmpty()) {
                log.info("fileSave");
                part.write(fileName);
            }

            BeanUtils.populate(reviewDTO, req.getParameterMap());
            HttpSession session = req.getSession();
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
            String emailId = memberDTO.getEmailId();
            reviewDTO.setEmailId(emailId);
            reviewDTO.setFileName("/upload/review/" + fileName);

            log.info(reviewDTO);   
            reviewService.addReview(reviewDTO);

        } catch (Exception e){
            log.info(reviewDTO);
            log.info(e.getMessage());
            throw new RuntimeException("add Error");
        }
        resp.sendRedirect("/products/product?pno=" + pno);
    }
}
