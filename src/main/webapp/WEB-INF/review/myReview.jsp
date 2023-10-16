<%@ page import="com.example.mongchi_shop.dto.MemberDTO" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
    String emailId = null;
    if (memberDTO != null) {
        emailId = memberDTO.getEmailId();
    }
%>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>리뷰</title>
    <%-- 별점 style --%>
    <style>
        .star {
            position: relative;
            font-size: 2rem;
            color: #ddd;
            word-wrap: normal;
        }
        .star input {
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            opacity: 0;
            cursor: pointer;
        }
        .star span {
            width: 0;
            height: 56px;
            position: absolute;
            left: 0;
            color: red;
            overflow: hidden;
            pointer-events: none;
        }

        .review-container .table {
            width: 100%;
        }
    </style>
    <%-- 별점 style/ --%>
</head>

<body>
<jsp:include page="/WEB-INF/inc/menu.jsp" />
<div class="hero">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>내가 쓴 리뷰목록</h1>
                </div>
            </div>
            <div class="col-lg-7">

            </div>
        </div>
    </div>
</div>

<!-- 주요 내용 섹션 -->
<div class="container mt-4">
    <div class="row">
        <!-- 리뷰 목록 -->
        <div class="col-xl-10 mx-auto">
            <br>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead class="thead-dark">
                    <tr align="center">
                        <th>번호</th>
                        <th>상품이미지</th>
                        <th>내용</th>
                        <th>별점</th>
                        <th>작성일</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <!-- 테이블 본문 -->
                    <tbody>
                    <c:forEach var="myReview" items="${myreviewDTOList}" varStatus="status">
                        <tr align="center">
                            <td>${status.index + 1}</td>
                            <td><img src="${myReview.fileName}" style="width: 100px"></td>
                            <td>${myReview.content}</td>
                            <td>
                                <span class="star">
                                    ★★★★★
                                    <span style="width: ${myReview.rate * 10}%;">★★★★★</span>
                                    <input type="range" value="${myReview.rate}" step="1" min="1" max="10">
                                </span>
                            </td>
                            <td>${myReview.addDate}</td>
                            <td>
                                <form action="/review/modify" method="get">
                                    <input type="hidden" name="rno" value="${myReview.rno}">
                                    <input type="hidden" name="pno" value="${myReview.pno}">
                                    <button class="btn btn-warning btn-sm" onclick="modfyReview(${myReview.rno}, ${myReview.pno})">수정</button>
                                </form>
                            </td>
                            <td>
                                <form action="/review/remove" method="get" onsubmit="return confirmDelete()">
                                    <input type="hidden" name="pno" value="${myReview.pno}">
                                    <input type="hidden" name="rno" value="${myReview.rno}">
                                    <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- 리뷰 목록/ -->
    </div>
</div>
<!-- 주요 내용 섹션/ -->

<script>
    // 삭제 여부를 확인하는 JavaScript 함수
    function confirmDelete() {
        let result = window.confirm("정말 삭제하시겠습니까?");
        return result;
    }
</script>
</body>
</html>
