package com.example.mongchi_shop.controller.cart;

import com.example.mongchi_shop.dto.CartDTO;
import com.example.mongchi_shop.dto.OrderDTO;
import com.example.mongchi_shop.dto.OrderItemDTO;
import com.example.mongchi_shop.service.CartService;
import com.example.mongchi_shop.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@WebServlet("/cart/shippingInfo")
public class shippingInfoController extends HttpServlet {
    private final OrderService ORDER_SERVICE = OrderService.INSTANCE;
    private final CartService CART_SERVICE = CartService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/shippingInfo(GET)...");

        HttpSession session = req.getSession();
        String orderId = (String) session.getAttribute("orderId");

        req.getRequestDispatcher("/WEB-INF/cart/shippingInfo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 전달받은 주문 데이터 Service로 전달
        log.info("/shippingInfo(POST)...");
        HttpSession session = req.getSession();
        List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");
        String orderId = (String) session.getAttribute("orderId");

        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemDTO> itemDTOList = new ArrayList<>();
        try {
            BeanUtils.populate(orderDTO, req.getParameterMap());
            log.info("orderDTO: " + orderDTO);

            // 주문한 상품 데이터 List에 담기
            for (CartDTO cartDTO : cartDTOList) {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .orderId(orderId)
                        .pno(cartDTO.getPno())
                        .productName(cartDTO.getProductName())
                        .cnt(cartDTO.getCnt())
                        .unitPrice(cartDTO.getUnitPrice())
                        .fileName(cartDTO.getFileName())
                        .build();
                itemDTOList.add(orderItemDTO);
            }

            boolean isRegister = ORDER_SERVICE.registerOrder(orderDTO, itemDTOList);
            if (isRegister) {
                int[] cnos = new int[cartDTOList.size()]; // 장바구니 번호 배열 생성
                int i = 0;
                for (CartDTO cartDTO : cartDTOList) {
                    cnos[i++] = cartDTO.getCno();
                }

                req.setAttribute("orderId", orderId);
                session.removeAttribute("cartDTOList");
                session.removeAttribute("orderId");
                CART_SERVICE.removeCart(cnos);
            }

            req.getRequestDispatcher("/WEB-INF/cart/thankCustomer.jsp").forward(req, resp);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ServletException("shippingInfo(POST) error");
        }
    }
}
