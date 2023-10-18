package com.example.mongchi_shop.service;

import com.example.mongchi_shop.dao.OrderDAO;
import com.example.mongchi_shop.domain.OrderItemVO;
import com.example.mongchi_shop.domain.OrderVO;
import com.example.mongchi_shop.dto.OrderDTO;
import com.example.mongchi_shop.dto.OrderItemDTO;
import com.example.mongchi_shop.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum OrderService {
    INSTANCE; // 싱글톤
    private OrderDAO orderDAO;
    private ModelMapper modelMapper;

    OrderService() {
        orderDAO = new OrderDAO();
        modelMapper = MapperUtil.INSTANCE.getInstance();
    }

    public boolean registerOrder(OrderDTO orderDTO, List<OrderItemDTO> itemDTOList) throws SQLException {
        log.info("registerOrder(OrderDTO orderDTO)...");
        log.info("orderDTO : " + orderDTO);

        OrderVO orderVO = modelMapper.map(orderDTO, OrderVO.class);
        log.info("orderVO : " + orderVO);

        boolean isRegister = orderDAO.insertOrder(orderVO);
        List<OrderItemDTO> dtoList = itemDTOList;
        for (OrderItemDTO orderItemDTO : dtoList) {
            OrderItemVO orderItemVO = modelMapper.map(orderItemDTO,OrderItemVO.class);
            orderDAO.insertOrderItem(orderItemVO);
        }

        return isRegister;
    }

    public OrderDTO getOrderByOno(int ono) throws SQLException {
        log.info("getOrderByOno(int ono)...");
        log.info("ono: " + ono);

        OrderVO orderVO = orderDAO.selectOrderByOno(ono);
        OrderDTO orderDTO = modelMapper.map(orderVO, OrderDTO.class);
        return orderDTO;
    }

    public List<OrderDTO> getOrderByEmailId(String emailId) throws SQLException {
        log.info("getOrderByEmailId(String emailId)...");
        log.info("emailId : " + emailId);

        List<OrderVO> orderVOList = orderDAO.selectOrderByEmailId(emailId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderVO orderVO : orderVOList) {
            OrderDTO orderDTO = modelMapper.map(orderVO, OrderDTO.class);

            List<OrderItemVO> itemVOList = orderDAO.selectOrderItemById(orderVO.getOrderId());
            List<OrderItemDTO> itemDTOList = new ArrayList<>();
            for (OrderItemVO itemVO : itemVOList) {
                OrderItemDTO itemDTO = modelMapper.map(itemVO, OrderItemDTO.class);
                itemDTOList.add(itemDTO);
            }

            orderDTO.setItemDTOList(itemDTOList);
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }

    public void removeOrder(int ono) throws SQLException {
        log.info("removeOrder(int ono)...");
        log.info("ono: " + ono);

        orderDAO.deleteOrder(ono);
    }
}
