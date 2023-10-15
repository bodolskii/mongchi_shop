package com.example.mongchi_shop.controller.cart;

import com.example.mongchi_shop.dto.CartDTO;
import com.example.mongchi_shop.service.CartService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Log4j2
@WebServlet("/cart/modify")
public class CartModifyController extends HttpServlet {
    private final CartService CART_SERVICE = CartService.INSTANCE;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/cart/modify(POST)...");
        int cno = Integer.parseInt(req.getParameter("cno"));
        int cnt = Integer.parseInt(req.getParameter("cnt"));
        String amount = req.getParameter("amount");
        log.info("cno: " + cno + ", cnt: " + cnt + ", amount: " + amount);

        cnt = amount.equals("increase") ? cnt + 1 : cnt - 1;

        HttpSession session = req.getSession();
        List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");

        try {
            boolean isModify = CART_SERVICE.modifyCnt(cno, cnt);
            if (isModify) {
                for (CartDTO cartDTO : cartDTOList) {
                    if (cartDTO.getCno() == cno) {
                        cartDTO.setCnt(cnt);
                        session.setAttribute("cartDTOList", cartDTOList);
                        break;
                    }
                }

                String jsonResponse = "{\"message\": \"수량이 업데이트되었습니다.\"}";

                // 응답을 클라이언트로 보냅니다.
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(jsonResponse);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("modify error");
        }
    }
}
