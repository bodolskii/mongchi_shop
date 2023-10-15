<%--
  Created by IntelliJ IDEA.
  User: 이헌구
  Date: 2023-09-27
  Time: 오후 2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>상품정보</title>
</head>
<body>
    <!-- Navigation Bar -->
    <jsp:include page="/WEB-INF/inc/menu.jsp" />

    <!-- Start Hero Section -->
    <div class="hero">
        <div class="container">
            <div class="row justify-content-between">
                <div class="col-lg-5">
                    <div class="intro-excerpt">
                        <h1>상품정보</h1>
                    </div>
                </div>
                <div class="col-lg-7">

                </div>
            </div>
        </div>
    </div>
    <!-- End Hero Section -->

    <div class="container" style="max-width: 1200px;">
        <h1 class="mt-5">상품 상세 페이지</h1>
        <div class="row mt-3">
            <div class="col-md-6" style="border: 1px solid #ededed;">
                <!-- 상품 이미지 -->
                <img src="${productDTO.fileName}" alt="상품 이미지" class="img-fluid">
            </div>
            <div class="col-md-6" style="border: 1px solid #ededed;">
                <!-- 상품 정보 -->
                <h2>${productDTO.productName}</h2>
                <h3 class="text-primary">${productDTO.unitPrice}원</h3>
                <hr>
                <p>${productDTO.description}</p>
                <p>
                    <span>주문수 ${productDTO.accumulatedOrders}</span>
                    <span>리뷰 ${productDTO.reviewCount}</span>
                </p>
                <form action="#" name="frmProduct" method="post">
                    <input type="hidden" name="pno" value="${productDTO.pno}">
                    <input type="hidden" name="productName" value="${productDTO.productName}">
                    <input type="hidden" name="unitPrice" value="${productDTO.unitPrice}">
                    <input type="hidden" name="fileName" value="${productDTO.fileName}">
                </form>
                <button id="btn-add" class="btn btn-primary">장바구니에 추가</button>
                <a href="/cart/list">장바구니로 이동</a>
                <a href="/qnaBoard/add?pno=${productDTO.pno}&productName=${productDTO.productName}">상품문의 등록</a>
            </div>
        </div>
        <div class="row mt-5">
            <div class="col">
                <!-- 상품 설명 -->
                <h3>상품 상세 설명</h3>
                <p>
                    이 부분에는 상품의 상세 설명이 들어갈 수 있습니다.
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                </p>
            </div>
        </div>
    </div>

  <script>
      document.addEventListener('DOMContentLoaded', function () {
          const frmProduct = document.querySelector('form[name=frmProduct]');
          console.log(frmProduct);
          const btnAdd = document.querySelector('#btn-add');
          console.log(btnAdd);

          btnAdd.addEventListener('click', function () {
              frmProduct.action = '/cart/add';
              frmProduct.submit();
          });
      });
  </script>
</body>
</html>
