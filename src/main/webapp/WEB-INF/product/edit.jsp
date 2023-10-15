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
    int pagePerBlock = 5;
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
    <a href="/admin/products/register">상품등록</a>
    <c:forEach var="product" items="${productDTOList}">
        <ul style="list-style: none;">
            <li>${product.pno}</li>
            <li>${product.pcode}</li>
            <li><a href="/products/product?pno=${product.pno}">${product.productName}</a></li>
            <li>${product.unitPrice}</li>
            <li>${product.description}</li>
            <li>${product.category}</li>
            <li>${product.unitsInstock}</li>
            <li><img src="${product.fileName}" alt="상품이미지" style="width: 100px"></li>
            <li>${product.accumulatedOrders}</li>
            <li>${product.reviewCount}</li>
            <li>${product.addDate}</li>
            <li>
                <a href="/admin/products/modify?pno=${product.pno}">수정</a>
                <a href="/admin/products/remove?pno=${product.pno}">삭제</a>
            </li>
        </ul>
    </c:forEach>

    <%-- 페이지 번호 --%>
    <div style="text-align: center">
        <c:set var="currentPage" value="<%= currentPage %>" />
        <a href="/admin/products?currentPage=1">첫 페이지</a>
        <c:if test="<%= currentBlock > 1 %>">
            <a href="/admin/products?currentPage=<%= firstPage - 1 %>">이전</a>
        </c:if>

        <c:forEach var="i" begin="<%= firstPage %>" end="<%= lastPage %>">
            <a href="/admin/products?currentPage=${i}">
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
            <a href="/admin/products?currentPage=<%= lastPage + 1 %>">다음</a>
        </c:if>
        <a href="/admin/products?currentPage=${totalPage}">마지막 페이지</a>
    </div>
</body>
</html>
