package com.example.mongchi_shop.dao;

import com.example.mongchi_shop.domain.MemberVO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2

public class MemberDAO {
    public void insertMember(MemberVO memberVO) throws SQLException {
        String sql = "insert into member (emailId, password, memberName, phone, birthday, addDate)" +
                " values (?, ?, ?, ?, ?, now())";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, memberVO.getEmailId());
        preparedStatement.setString(2, memberVO.getPassword());
        preparedStatement.setString(3, memberVO.getMemberName());
        preparedStatement.setString(4, memberVO.getPhone());
        preparedStatement.setString(5, memberVO.getBirthday());
        preparedStatement.executeUpdate();
    }
    public MemberVO getWithPassword(String emailId, String password) throws SQLException {
        String sql="SELECT * FROM member WHERE emailId = ? AND password = ?";

        @Cleanup Connection connection=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, emailId);
        preparedStatement.setString(2, password);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        MemberVO memberVO = null;
        if(resultSet.next()) {
            memberVO = MemberVO.builder()
                    .mno(resultSet.getInt("mno"))
                    .emailId(resultSet.getString("emailId"))
                    .password(resultSet.getString("password"))
                    .memberName(resultSet.getString("memberName"))
                    .zipCode(resultSet.getString("zipCode"))
                    .address01(resultSet.getString("address01"))
                    .address02(resultSet.getString("address02"))
                    .uuid(resultSet.getString("uuid"))
                    .birthday(resultSet.getString("birthday"))
                    .phone(resultSet.getString("phone"))
                    .build();
        }

        return memberVO;
    }

    public void updateUuid(String emailId, String uuid) throws SQLException {
        // 자동로그인 기능 사용해서 임의의 문자열이 생성된 경우, 해당 memberId의 데이터에 업데이트
        String sql = "UPDATE member SET uuid = ? WHERE emailId = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, emailId);
        preparedStatement.executeUpdate();
    }
    public void updateMember(MemberVO memberVO) throws SQLException {
        // 자동로그인 기능 사용해서 임의의 문자열이 생성된 경우, 해당 memberId의 데이터에 업데이트
        String sql="UPDATE member SET phone = ? , zipCode = ?, address01 = ?, address02 = ?,address03 = ?  WHERE emailId=?";
        @Cleanup Connection connection=ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setString(1, memberVO.getPhone());
        preparedStatement.setString(2, memberVO.getZipCode());
        preparedStatement.setString(3, memberVO.getAddress01());
        preparedStatement.setString(4, memberVO.getAddress02());
        preparedStatement.setString(5, memberVO.getAddress03());
        preparedStatement.setString(6, memberVO.getEmailId());
        preparedStatement.executeUpdate();
    }

    public MemberVO selectUuid(String uuid) throws SQLException {
        String sql="SELECT * FROM member where uuid=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, uuid);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        MemberVO memberVO = null;
        if(resultSet.next()) {
            memberVO = MemberVO.builder()
                    .emailId(resultSet.getString("emailId"))
                    .memberName(resultSet.getString("memberName"))
                    .zipCode(resultSet.getString("zipCode"))
                    .address01(resultSet.getString("address01"))
                    .address02(resultSet.getString("address02"))
                    .build();
        }

        return memberVO;
    }
    public void deleteMember(String emailId) throws SQLException {
        String sql = "delete from member where emailId = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, emailId);
        preparedStatement.executeUpdate();
    }
    public void updatePassword(String emailId,String password) throws SQLException {
        String sql = "update member set password = ? where emailId = ?";
        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,password);
        preparedStatement.setString(2,emailId);
        preparedStatement.executeUpdate();
    }
}
