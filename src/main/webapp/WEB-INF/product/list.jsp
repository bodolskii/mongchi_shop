<%@ page import="java.util.List" %>
<%@ page import="com.example.mongchi_shop.dto.ProductDTO" %><%--
  Created by IntelliJ IDEA.
  User: 이헌구
  Date: 2023-09-27
  Time: 오후 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<ProductDTO> productDTOList = (List<ProductDTO>) request.getAttribute("productDTOList");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPage = (int) request.getAttribute("totalPage");
    int pagePerBlock = 4;
    int totalBlock = totalPage % pagePerBlock == 0 ? totalPage/ pagePerBlock : totalPage /pagePerBlock + 1;
    int currentBlock = ((currentPage - 1) / pagePerBlock) + 1;
    int firstPage = ((currentBlock - 1) * pagePerBlock) + 1;
    int lastPage = currentBlock * pagePerBlock;
    lastPage = (lastPage > totalPage) ? totalPage : lastPage;
%>
<html>
<head>
    <title>상품 목록</title>
</head>
<body>
<!-- Start UI 공통 (복붙) -->
<!-- Navigation Bar -->
    <jsp:include page="/WEB-INF/inc/menu.jsp" />

<!-- Start Hero Section -->
    <div class="hero">
        <div class="container">
            <div class="row justify-content-between">
                <div class="col-lg-5">
                    <div class="intro-excerpt">
                        <h1>상품목록</h1>
                    </div>
                </div>
                <div class="col-lg-7">

                </div>
            </div>
        </div>
    </div>
<!-- End Hero Section -->
<!-- End UI 공통 (복붙) -->

    <div class="untree_co-section product-section before-footer-section">
        <div class="container">
            <div class="row">

                <c:forEach var="product" items="${productDTOList}">
                    <!-- Start Column -->
                    <div class="col-12 col-md-4 col-lg-3 mb-5">
                        <a class="product-item" href="/products/product?pno=${product.pno}">
                            <img src="${product.fileName}" class="img-fluid product-thumbnail" style="height: 70%;">
                            <h3 class="product-title">${product.productName}</h3>
                            <strong class="product-price">${product.unitPrice}원</strong>

                            <span class="icon-cross">
								<img src="images/cross.svg" class="img-fluid">
							</span>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <%-- 페이지 번호 --%>
            <div style="text-align: center">
                <c:set var="currentPage" value="<%= currentPage %>" />
                <a href="/products?currentPage=1">첫 페이지</a>
                <c:if test="<%= currentBlock > 1 %>">
                    <a href="/products?currentPage=<%= firstPage - 1 %>">이전</a>
                </c:if>

                <c:forEach var="i" begin="<%= firstPage %>" end="<%= lastPage %>">
                    <a href="/products?currentPage=${i}">
                        <c:choose>
                            <c:when test="${currentPage == i}">
                                <span style="color: #4C5317;"><b>[${i}]</b></span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: #4C5317;">[${i}]</span>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </c:forEach>

                <c:if test="<%= currentBlock < totalBlock %>">
                    <a href="/products?currentPage=<%= lastPage + 1 %>">다음</a>
                </c:if>
                <a href="/products?currentPage=${totalPage}">마지막 페이지</a>
            </div>
        </div>
    </div>

</body>
</html>
