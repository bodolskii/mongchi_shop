<%@ page import="com.example.mongchi_shop.dto.CartDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2023-10-14
  Time: 오후 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="untree_co-section before-footer-section">
    <div class="container">
        <div class="row mb-5">
            <form class="col-md-12" name="frmCart" method="get">
                <div class="site-blocks-table">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="product-thumbnail">Image</th>
                            <th class="product-name">Product</th>
                            <th class="product-price">Price</th>
                            <th class="product-quantity">Quantity</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            int total = 0; // 총 주문금액
                            for (CartDTO cart : cartDTOList) {
                                int price = cart.getUnitPrice() * cart.getCnt();
                                total += price;
                        %>
                        <tr>
                            <td class="product-thumbnail">
                                <img src="<%= cart.getFileName() %>" alt="Image" class="img-fluid">
                            </td>
                            <td class="product-name">
                                <h2 class="h5 text-black"><%= cart.getProductName() %></h2>
                            </td>
                            <td><%= cart.getUnitPrice() %>원</td>
                            <td><%= cart.getCnt() %></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="row mb-5">
                    <div class="col-md-10">
                        <button href="/cart/list" id="btn-products" class="btn btn-outline-black btn-sm btn-block">장바구니 상세페이지</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- qna ----------------------------------------------->


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>
