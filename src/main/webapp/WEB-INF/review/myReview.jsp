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
        body {
            height: 1500px;
        }

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
            border: none; /* Remove table border */
        }

        /* Increase padding for better content area */
        .review-container .table th,
        .review-container .table td {
            padding: 15px;
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
        <div class="col-xl-auto mx-auto">
            <br>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead class="thead-dark">
                    <tr align="center">
                        <th>번호</th>
                        <th>상품이미지</th>
                        <th>내용</th>
                        <th>별점</th>
                        <th>리뷰 작성일</th>
                        <th colspan="2">비고</th>
                    </tr>
                    </thead>
                    <!-- 테이블 본문 -->
                    <tbody align="center">
                    <c:forEach var="myReview" items="${myreviewDTOList}" varStatus="status">
                        <tr align="center" class="writer">
                            <td style="font-size: 20px; vertical-align: middle;">${status.index + 1}</td>
                            <td style="vertical-align: middle"><img src="${myReview.fileName}" style="width: 100px;"></td>
                            <td style="width: 500px; vertical-align: middle;" >${myReview.content}</td>
                            <td style="vertical-align: middle">
                                <span class="star">
                                    ★★★★★
                                    <span style="width: ${myReview.rate * 10}%;">★★★★★</span>
                                    <input type="range" value="${myReview.rate}" step="1" min="1" max="10">
                                </span>
                            </td>
                            <td style="vertical-align: middle;">
                                    ${myReview.addDate}
                            </td>
                            <td>
                                <form action="/review/modify" method="get" class="buttons">
                                    <input type="hidden" name="rno" value="${myReview.rno}">
                                    <input type="hidden" name="pno" value="${myReview.pno}">
                                    <button class="btn btn-warning btn-sm" onclick="modfyReview(${myReview.rno}, ${myReview.pno})"
                                            style="background-color: #0f5132; border: #0f5132; margin-top: 30px" >수정</button>
                                </form>
                            </td>
                            <td>
                                <form action="/review/remove" method="get" onsubmit="return confirmDelete()">
                                    <input type="hidden" name="pno" value="${myReview.pno}">
                                    <input type="hidden" name="rno" value="${myReview.rno}">
                                    <button type="submit" class="btn btn-danger btn-sm"
                                            style="background-color: #fa1b1b; border: #fa1b1b; margin-top: 30px" >삭제</button>
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
    // 리뷰 작성 폼 제출 전에 별점과 리뷰가 입력되었는지 확인
    document.querySelector('#reviewForm').addEventListener('submit', function (event) {
        const selectedRate = document.querySelector("input[name=rate]").value;
        const selectedText = document.querySelector("textarea[name=content]").value;

        console.log("Selected Rate:", selectedRate);
        console.log("Selected Text:", selectedText);

        // 별점을 입력하지 않았을 경우
        if (selectedRate === "") {
            alert("별점을 선택해주세요");
            event.preventDefault(); // 폼 제출을 막음
            return; // 다음 if절이 시행되지 않도록 return
        }

        // 리뷰를 작성하지 않았을 경우
        if (selectedText === "") {
            alert("리뷰를 작성해주세요")
            event.preventDefault(); // 폼 제출을 막음
        }
    });

    // 삭제 여부를 확인하는 JavaScript 함수
    function confirmDelete() {
        let result = window.confirm("정말 삭제하시겠습니까?");
        return result;
    }
</script>
</body>
</html>
