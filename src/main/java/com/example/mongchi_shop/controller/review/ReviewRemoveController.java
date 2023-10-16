package com.example.mongchi_shop.controller.review;

import com.example.mongchi_shop.service.ReviewService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet ("/review/remove")
public class ReviewRemoveController extends HttpServlet {

    private final ReviewService reviewService = ReviewService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/remove GET()...");

        try {
            reviewService.removeReview(Integer.parseInt(req.getParameter("rno")));
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("remove error...");
        }
        log.info("pno : " + req.getParameter("pno"));
        resp.sendRedirect("/review/myReview");
    }

}
