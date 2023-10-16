package com.example.mongchi_shop.controller.review;

import com.example.mongchi_shop.domain.ReviewVO;
import com.example.mongchi_shop.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@WebServlet ("/review/*")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, location = "c:/upload")
public class ReviewListController extends HttpServlet {

    ReviewService reviewService = ReviewService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // productViewController로 이동.
//        try {
//            // ReviewService에서 리뷰 데이터를 가져오기
//            List<ReviewVO> reviewVOList = reviewService.getReview(req);
//            log.info(reviewVOList);
//
//            // 리뷰 데이터를 request에 attribute로 추가
//            req.setAttribute("reviewVOList", reviewVOList);
//
//            // list.jsp로 포워딩
//            req.getRequestDispatcher("/WEB-INF/review/list.jsp").forward(req, resp);
//        } catch (SQLException | ClassNotFoundException ex) {
//            log.error("Error processing doGet", ex);
//            throw new ServletException("Error processing doGet", ex);
//        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ReviewURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = ReviewURI.substring(contextPath.length());

        log.info("command : " + command);   // 도메인 이후의 경로를 불러옴
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        // 리스트 목록
        try {
            List<ReviewVO> reviewVOS = reviewService.getReview(1);
            JSONArray jsonArray = new JSONArray();
            log.info("review " + reviewVOS);
            for (ReviewVO reviewVO : reviewVOS) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rno",reviewVO.getRno());
                jsonObject.put("emailId", reviewVO.getEmailId());
                jsonObject.put("pno", reviewVO.getPno());
                jsonObject.put("content", reviewVO.getContent());
                jsonObject.put("rate", reviewVO.getRate());
                jsonObject.put("addDate", reviewVO.getAddDate());
                jsonObject.put("fileName", reviewVO.getFileName());
                jsonArray.add(jsonObject);
            }
            resp.getWriter().print(jsonArray.toJSONString());
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
