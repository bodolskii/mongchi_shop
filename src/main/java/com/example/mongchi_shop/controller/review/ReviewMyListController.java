package com.example.mongchi_shop.controller.review;

import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.dto.ReviewDTO;
import com.example.mongchi_shop.service.ReviewService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Log4j2
@WebServlet("/review/myReview")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, location = "c:/upload/review")

public class ReviewMyListController extends HttpServlet {

    ReviewService reviewService = ReviewService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/review/myReview doGet()...");
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        try {
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = memberDTO.getEmailId();
        List<ReviewDTO> myreviewDTOList = reviewService.getReviewByEmailId(emailId);

        req.setAttribute("myreviewDTOList", myreviewDTOList);
        req.getRequestDispatcher("/WEB-INF/review/myReview.jsp").forward(req,resp);

        } catch (Exception ex) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
