<%@ page import="com.example.mongchi_shop.dto.ProductDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mongchi_shop.dto.CartDTO" %>
<%@ page import="com.example.mongchi_shop.dto.OrderDTO" %>
<%@ page import="com.example.mongchi_shop.dto.OrderItemDTO" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2023-10-13
  Time: 오전 6:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<OrderDTO> orderDTOList = (List<OrderDTO>) request.getAttribute("orderDTOList");
%>

<html>
<head>
    <title>마이페이지</title>
</head>

<body>
<jsp:include page="/WEB-INF/inc/menu.jsp"/>
<nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">
    <div class="container">
        <a class="navbar-brand">주문내역<span>.</span></a>
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
                <li><a class="nav-link" href="/member/myorder"> 내구매목록 </a></li>
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
                            <th class="product-thumbnail">상품정보</th>
                            <th class="product-name">주문일자</th>
                            <th class="product-quantity">주문번호</th>
                            <th class="product-quantity">주문금액(수량)</th>
                            <th class="product-quantity">주문상태</th>
                            <th class="product-quantity">비고</th>
                        </tr>
                        </thead>
                        <tbody>

                        <%
                            for (OrderDTO order : orderDTOList) {
                        %>
                            <%
                                for (OrderItemDTO itemDTO : order.getItemDTOList()) {
                            %>
                            <tr>
                                <td class="product-thumbnail">
                                    <img src="<%= itemDTO.getFileName() %>">
                                    <span><%= itemDTO.getProductName() %></span>
                                </td>
                                <td class="product-name">
                                    <h2 class="h5 text-black"><%= order.getOrderDate() %></h2>
                                </td>
                                <td class="product-name">
                                    <h2 class="h5 text-black"><%= order.getOrderId() %></h2>
                                </td>
                                <td class="product-name">
                                    <h2 class="h5 text-black"><%= itemDTO.getUnitPrice() * itemDTO.getCnt() %>원</h2>
                                    <span><%= itemDTO.getCnt() %>개</span>
                                </td>
                                <td class="product-name">
                                    <h2 class="h5 text-black"><%= order.getOrderStatus() %></h2>
                                </td>
                                <td>
                                    <button>리뷰작성</button>
                                </td>
                            </tr>
                            <%
                                }
                            %>
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
