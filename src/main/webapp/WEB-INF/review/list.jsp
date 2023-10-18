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

    </style>
    <%-- 별점 style/ --%>
</head>

<body>
<!-- 헤더 섹션 -->
<div class="jumbotron bg-primary text-white">
    <div class="container">
        <h1 class="display-3">리뷰</h1>
    </div>
</div>
<!-- 헤더 섹션/ -->

<!-- 주요 내용 섹션 -->
<div class="container mt-4">
    <div class="row">
        <!-- 리뷰 목록 -->
        <div class="col-md-8">
            <div class="card review-container">
                <div class="card-body">
                    <h2 class="card-title review-header">리뷰 목록</h2>
                    <div class="table-responsive">
                        <!-- 테이블 헤더 -->
                        <table class="table">
                            <thead>
                            <tr class="title" align="center">
                                <th>번호</th>
                                <th>상품이미지</th>
                                <th>내용</th>
                                <th>아이디</th>
                                <th>별점</th>
                                <th>작성일</th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <!-- 테이블 본문 -->
                            <tbody>
                            <c:forEach var="reviewVO" items="${reviewVOList}" varStatus="status">
                                <tr class="content" align="center">
                                    <td>${status.index + 1}</td>
                                    <td><img src="${reviewVO.fileName}" style="width: 100px"></td>
                                    <td>${reviewVO.content}</td>
                                    <td>${reviewVO.emailId}</td>
                                    <td>
                                        <span class="star">
                                            ★★★★★
                                            <span style="width: ${reviewVO.rate * 10}%;">★★★★★</span>
                                            <input type="range" value="${reviewVO.rate}" step="1" min="1" max="10">
                                        </span>
                                    </td>
                                    <td>${reviewVO.addDate}</td>
                                    <td>
                                        <form action="/review/modify" method="get">
                                            <input type="hidden" name="rno" value="${reviewVO.rno}">
                                            <input type="hidden" name="pno" value="${reviewVO.pno}">
                                            <c:set var="emailId" value="<%= emailId %>"/>
                                            <c:if test="${emailId eq reviewVO.emailId}">
                                                <button class="btn btn-warning btn-sm"onclick="modfyReview(${reviewVO.rno}, ${reviewVO.pno})">수정</button>
                                            </c:if>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/review/remove" method="get" onsubmit="return confirmDelete()">
                                            <input type="hidden" name="pno" value="${reviewVO.pno}">
                                            <input type="hidden" name="rno" value="${reviewVO.rno}">
                                            <c:set var="emailId" value="<%= emailId %>"/>
                                            <c:if test="${emailId eq reviewVO.emailId}">
                                                <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                                            </c:if>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- 리뷰 목록/ -->

        <!-- 리뷰 작성 섹션 -->
        <div class="col-md-4">
            <div class="card review-form">
                <div class="card-body">
                    <h2 class="card-title review-header">리뷰 작성</h2>
                    <form id="reviewForm" action="<%= String.format("/review/add?pno=%s", request.getParameter("pno")) %>" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <span class="star">
                                  ★★★★★
                                  <span class="rate">★★★★★</span>
                                  <input class="rate" type="range" value="1" step="1" min="1" max="10">
                            </span>
                            <textarea class="form-control" name="content" rows="3" placeholder="리뷰를 작성하세요"></textarea>
                        </div>
                        <input type="hidden" name="rate">
                        <input type="file" name="fileName" class="form-control">
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block add-review-btn">리뷰 작성</button>
                        </div>
                    </form>
                </div>
            </div>
        </div> <!-- 리뷰 작성/ -->
    </div>
</div>
<!-- 주요 내용 섹션/ -->

<script>
    // 별점 관련 이벤트
    document.addEventListener('DOMContentLoaded', function () {
        const star_input = document.querySelector('.star > input.rate');
        const star_span = document.querySelector('.star > .rate');

        // 별점 드래그 할 때
        star_input.addEventListener('input', () => {
            console.log(star_input.value);
            let rate = (star_input.value * 10);
            star_span.style.width = rate + "%";

            document.querySelector("input[name=rate]").value = rate / 10;
            console.log("rate :" + document.querySelector("input[name=rate]").value);
            console.log(rate);
        });
    });

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