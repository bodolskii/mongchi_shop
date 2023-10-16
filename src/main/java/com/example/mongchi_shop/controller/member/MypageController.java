package com.example.mongchi_shop.controller.member;

import com.example.mongchi_shop.dto.CartDTO;
import com.example.mongchi_shop.dto.MemberDTO;
import com.example.mongchi_shop.dto.QnABoardDTO;
import com.example.mongchi_shop.service.CartService;
import com.example.mongchi_shop.service.QnABoardService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@WebServlet("/member/mypage")
public class MypageController extends HttpServlet {

    final int ROW_PER_PAGE = 10; // 페이지에 보일 주문 개수
    private final QnABoardService qnaBoardService = QnABoardService.INSTANCE;
    //

    private final CartService CART_SERVICE = CartService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //
        HttpSession session = req.getSession();
        String orderId = (String) session.getAttribute("orderId");
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
        String emailId = memberDTO.getEmailId();


        List<CartDTO> cartDTOList = null;
        try {
            cartDTOList = CART_SERVICE.getCartByOrderId(orderId);

            log.info("cartDTOList: " + cartDTOList);

            session.setAttribute("cartDTOList", cartDTOList);


            //QnA
            int currentPage = 1;
            int limit = ROW_PER_PAGE;
            if (req.getParameter("currentPage") != null) {
                currentPage = Integer.parseInt(req.getParameter("currentPage"));
            }

//        int pno = Integer.parseInt(req.getParameter("pno"));
//        log.info(pno);


            int totalRecord = 0;

            totalRecord = qnaBoardService.getMyQnAListCount(emailId);

            log.info(totalRecord);
            List<QnABoardDTO> qnABoardDTOList = null;

            qnABoardDTOList = qnaBoardService.getQnABoardByEmailId(emailId, currentPage, limit);


            int totalPage;
            if (totalRecord % limit == 0) {
                totalPage = totalRecord / limit;
                Math.floor(totalPage);
            } else {
                totalPage = totalRecord / limit;
                Math.floor(totalPage);
                totalPage = totalPage + 1;
            }

            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPage", totalPage);
            req.setAttribute("qnABoardDTOList", qnABoardDTOList);

            log.info("qnABoardDTOList: " + qnABoardDTOList);

            req.setAttribute("ROW_PER_PAGE", ROW_PER_PAGE);
            req.getRequestDispatcher("/WEB-INF/member/mypage.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


