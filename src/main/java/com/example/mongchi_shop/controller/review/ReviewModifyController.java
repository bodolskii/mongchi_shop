package com.example.mongchi_shop.controller.review;

import com.example.mongchi_shop.dto.ReviewDTO;
import com.example.mongchi_shop.service.ReviewService;
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
@WebServlet("/review/modify")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, location = "c:/upload")
public class ReviewModifyController extends HttpServlet {

    private final ReviewService reviewService = ReviewService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/modify doGet()...");
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");
        int rno = Integer.parseInt(req.getParameter("rno"));
        log.info("rno : " + rno);
        int pno = Integer.parseInt(req.getParameter("pno"));
        log.info("pno : " + pno);
        try {
            log.info("/review/modify doGet() try...");
            ReviewDTO reviewDTO = reviewService.getReviewByPno(pno,rno);
            log.info("reviewDTO : " + reviewDTO);
            req.setAttribute("reviewDTO", reviewDTO);
            log.info("fileName : " + reviewDTO.getFileName());
            req.getRequestDispatcher("/WEB-INF/review/modify.jsp").forward(req, resp);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("ReviewModify doGet error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/modify doPost()...");
        ReviewDTO reviewDTO = new ReviewDTO();
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        try {
            Part part = req.getPart("fileName");
            String fileName = reviewService.getFileName(part);
            log.info("fileName = " + fileName);
            BeanUtils.populate(reviewDTO,req.getParameterMap());
            
            if (!fileName.isEmpty()) {
                reviewDTO.setFileName("/upload/" + fileName);
                /* 새로 업로드 될 파일 */
            }
            else {
                reviewDTO.setFileName(req.getParameter("orifileName"));
                /* 기존의 파일 */
            }
            reviewService.modifyReview(reviewDTO);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("ReviewModify doPost error");
        }
        resp.sendRedirect("/review/list?pno=" + req.getParameter("pno"));
    }
}
