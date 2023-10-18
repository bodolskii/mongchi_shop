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
    <title>리뷰수정</title>

  <style>

    body {
      height: 1500px;
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
<!-- 리뷰 수정 폼 -->
<div class="hero">
  <div class="container">
    <div class="row justify-content-between">
      <div class="col-lg-5">
        <div class="intro-excerpt">
          <h1>나의 리뷰수정</h1>
        </div>
      </div>
      <div class="col-lg-7">
      </div>
    </div>
  </div>
</div>
<br>
<div class="container mt-4" align="center">
  <div class="card review-form">
    <div class="card-body">
        <form id="modifyReviewForm" action="/review/modify" method="post" enctype="multipart/form-data">
            <input type="text" name="orifileName" value="${reviewDTO.fileName}">
            <input type="hidden" name="pno" value="${reviewDTO.pno}">
            <input type="hidden" name="rno" value="${reviewDTO.rno}">
            <input type="hidden" name="rate" value="${reviewDTO.rate}">
            <input type="hidden" value="${reviewDTO.fileName}">
        <div class="form-group">
          <br>
          <label class="fieldStar" style="font-size: 30px"><b>나의 별점</b></label><br>
          <input type="hidden" value="${reviewDTO.rate}">
          <span class="star">
                ★★★★★
                <span class="rate" style="width: ${reviewDTO.rate * 10}%;">★★★★★</span>
                <input class="rate" type="range" value="${reviewDTO.rate * 10}" step="1" min="1" max="10">
          </span>
        </div>
        <br>
        <div class="form-group">
            <div class="float-start">
                <h2><label for="modifyContent"><b>리뷰내용</b></label></h2>
            </div>
            <input type="hidden" name="addDate" value="${reviewDTO.addDate}">
            <input type="text" class="form-control" id="modifyContent" name="content" rows="3"  value="${reviewDTO.content}" placeholder="내용을 작성하세요">
        </div>

        <input type="hidden" name="rate" value="${reviewDTO.rate * 10}%">
        <input type="file" name="fileName" class="form-control" style="height: 38px"><br>
        <div class="float-end">
        <button type="submit" class="btn btn-primary">수정 완료</button>
        <button type="button" class="btn btn-danger" onclick="cancelModification()"
                  style="background-color: #fa0000; border: #fa1b1b" >취소</button>
        </div>
      <%--  <div class="btn_class">
        <button type="submit" class="btn btn-primary">수정 완료</button>
        <button type="button" class="btn btn-danger" onclick="cancelModification()">취소</button>
        </div>--%>
      </form>
    </div>
  </div>
</div>


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

  // 취소를 눌렀을 시 이전 페이지로 이동
  function cancelModification() {
    window.history.back();
  }
</script>
</body>
</html>
