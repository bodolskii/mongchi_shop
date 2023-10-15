package com.example.mongchi_shop.dao;

import com.example.mongchi_shop.domain.CartVO;
import com.example.mongchi_shop.domain.ProductVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public void insertCart(CartVO cartVO) throws SQLException {
        // 동일한 주문번호에 같은 상품번호가 있으면 업데이트
        String sql = "select cno from cart where orderId = ? and pno = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cartVO.getOrderId());
        preparedStatement.setInt(2, cartVO.getPno());
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            sql = "update cart set cnt = cnt + 1 where cno = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, resultSet.getInt("cno"));
            preparedStatement.executeUpdate();
        }
        else {
            String emailId = cartVO.getEmailId();
            emailId = emailId == null ? "Guest" : emailId;

            int cnt = 1;
            sql = "insert into cart (orderId, emailId, cnt, addDate, pno, productName, unitPrice, fileName)" +
                    " values (?, ?, ?, now(), ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cartVO.getOrderId());
            preparedStatement.setString(2, emailId);
            preparedStatement.setInt(3, cnt);
            preparedStatement.setInt(4, cartVO.getPno());
            preparedStatement.setString(5, cartVO.getProductName());
            preparedStatement.setInt(6, cartVO.getUnitPrice());
            preparedStatement.setString(7, cartVO.getFileName());
            preparedStatement.executeUpdate();
        }
    }
    
    public void updateOrderId(String orderId, String emailId) throws SQLException {
        // 주문번호 업데이트
        String sql = "update cart set orderId = ? where emailId = ? and orderId != ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, orderId);
        preparedStatement.setString(2, emailId);
        preparedStatement.setString(3, orderId);
        preparedStatement.executeUpdate();
    }

    public List<CartVO> selectCartByOrderId(String orderId) throws SQLException {

        // orderId와 일치하는 데이터 찾기
        String sql = "select * from cart where orderId = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, orderId);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<CartVO> list = new ArrayList<>();
        while (resultSet.next()) {
            // 데이터베이스의 데이터를 CartVO 객체에 저장해서 list에 추가
            CartVO cartVO = CartVO.builder()
                    .cno(resultSet.getInt("cno"))
                    .orderId(resultSet.getString("orderId"))
                    .emailId(resultSet.getString("emailId"))
                    .cnt(resultSet.getInt("cnt"))
                    .addDate(resultSet.getString("addDate"))
                    .pno(resultSet.getInt("pno"))
                    .productName(resultSet.getString("productName"))
                    .unitPrice(resultSet.getInt("unitPrice"))
                    .fileName(resultSet.getString("fileName"))
                    .build();
            list.add(cartVO);
        }

        return list;
    }
    
    public void deleteCart(int cno) throws SQLException {
        // cno와 일치하는 데이터 삭제
        String sql = "delete from cart where cno = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, cno);
        preparedStatement.executeUpdate();
    }

    public boolean updateCnt(int cno, int cnt) throws SQLException {
        // 전달받은 cnt 업데이트
        String sql = "update cart set cnt = ? where cno = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, cnt);
        preparedStatement.setInt(2, cno);
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    }
}
