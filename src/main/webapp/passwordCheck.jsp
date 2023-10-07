<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.example.mongchi_shop.dao.ConnectionUtil" %>
<%@ page import="com.example.mongchi_shop.dto.MemberDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String password = request.getParameter("password");
    String emailId = request.getParameter("emailId");

    Connection connection = ConnectionUtil.INSTANCE.getConnection();
    String sql = "select * from member where emailId = ? and password=?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet resultSet = null;
    preparedStatement.setString(1, emailId);
    preparedStatement.setString(2, password);
    resultSet = preparedStatement.executeQuery();
    if(resultSet.next()) {
        out.print("{\"result\":\"true\"}");
    }else {
        out.print("{\"result\":\"false\"}");
    }
    if (connection != null)connection.close();
    if (preparedStatement!= null)preparedStatement.close();
    if(resultSet != null) resultSet.close();
%>
