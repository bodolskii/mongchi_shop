<%@ page import="com.example.mongchi_shop.dto.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mongchi_shop.dto.CartDTO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2023-10-13
  Time: 오전 6:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");
%>

<html>
<head>
    <title>마이페이지</title>
</head>

<body>
<jsp:include page="/WEB-INF/inc/menu.jsp"/>
<nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">

    <div class="container">
        <a class="navbar-brand">마이 페이지<span>.</span></a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsFurni">
            <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
                <li class="nav-item active">
                    <a class="nav-link" id="cart" href="/member/mypage"> 장바구니 </a>
                </li>
                <li><a class="nav-link" href="/member/myQnA"> QnA </a></li>
                <li><a class="nav-link" href="/review/myReview"> 나의 리뷰 </a></li>
                <li><a class="nav-link" href="/member/modify"> 내정보수정 </a></li>
            </ul>
        </div>
    </div>
</nav>


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






<jsp:include page="/WEB-INF/inc/footer.jsp" />
</body>
</html>
