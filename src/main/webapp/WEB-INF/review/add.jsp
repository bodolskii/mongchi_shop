<%@ page import="com.example.mongchi_shop.dto.MemberDTO" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>리뷰 작성</title>

    <style>
        body {
            height: 1000px;
        }
        /* 별점style */

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
<jsp:include page="/WEB-INF/inc/menu.jsp" />
<!-- 리뷰 작성 폼 -->
<div class="hero">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-lg-5">
                <div class="intro-excerpt">
                    <h1>리뷰작성</h1>
                </div>
            </div>
            <div class="col-lg-7">
            </div>
        </div>
    </div>
</div><br>

<!-- 리뷰 작성 섹션 -->
<div class="container mt-4" align="center">
    <div class="card review-form">
        <div class="card-body"><br>
            <h2 class="card-title review-header">리뷰 작성</h2>
            <form id="reviewForm" action="/review/add?pno=1" method="post" enctype="multipart/form-data">
                <%--<%= String.format("/review/add?pno=%s", request.getParameter("pno")) %>--%>
                <div class="form-group">
                            <span class="star">
                                  ★★★★★
                                  <span class="rate">★★★★★</span>
                                  <input class="rate" type="range" value="1" step="1" min="1" max="10">
                            </span><br><br>
                    <textarea class="form-control" name="content" rows="3" placeholder="리뷰를 작성하세요"></textarea>
                </div>
                <input type="hidden" name="rate">
                <input type="file" name="fileName" class="form-control" style="height: 38px"><br>
                <input type="hidden" name="logoFile">
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block add-review-btn">리뷰 작성</button>
                </div>
            </form>
        </div>
    </div>
</div> <!-- 리뷰 작성/ -->


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
        })
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

</script>
</body>
</html>
