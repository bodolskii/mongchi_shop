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
<!-- 리뷰 수정 폼 -->
<div class="hero">
  <div class="container">
    <h1><span class="d-block">리뷰 수정</span></h1>
  </div>
</div>

<form id="modifyReviewForm" action="/review/modify" method="post" enctype="multipart/form-data">
  <input type="text" name="orifileName" value="${reviewDTO.fileName}">
  <input type="hidden" name="pno" value="${reviewDTO.pno}">
  <input type="hidden" name="rno" value="${reviewDTO.rno}">
  <input type="hidden" name="rate" value="${reviewDTO.rate}">
  <input type="hidden" value="${reviewDTO.fileName}">
  <div class="form-group">
    <label>별점</label>
    <input type="hidden" value="${reviewDTO.rate}">
    <span class="star">
          ★★★★★
          <span class="rate" style="width: ${reviewDTO.rate * 10}%;">★★★★★</span>
          <input class="rate" type="range" value="${reviewDTO.rate * 10}" step="1" min="1" max="10">
    </span>
  </div>

  <div class="form-group">
    <label for="modifyContent">내용</label>
    <input type="hidden" name="addDate" value="${reviewDTO.addDate}">
    <input type="text" class="form-control" id="modifyContent" name="content" rows="3"  value="${reviewDTO.content}" placeholder="내용을 작성하세요"></input>
  </div>

  <input type="hidden" name="rate" value="${reviewDTO.rate * 10}%">
  <input type="file" name="fileName" class="form-control">
  <button type="submit" class="btn btn-primary">수정 완료</button>
  <button type="button" class="btn btn-danger" onclick="cancelModification()">취소</button>
</form>


<script>

  // 별점 관련 이벤트
  document.addEventListener('DOMContentLoaded', function () {
    const star_input = document.querySelector('.star > input.rate');
    const star_span = document.querySelector('.star > .rate');

    /* console.log(star_input);
     console.log(star_span);*/
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

  // 리뷰 수정
  document.addEventListener('DOMContentLoaded', function () {
    const modifyForm = document.getElementById('modifyReviewForm');
    const modifyButton = modifyForm.querySelector('button');

    modifyButton.addEventListener('click', function () {
      const reviewRno = modifyForm.querySelector('[name="reviewRno"]').value;
      const reviewPno = modifyForm.querySelector('[name="reviewPno"]').value;

      modifyReviewForm(reviewRno, reviewPno);
      modifyForm.submit(); // modifyReview 호출 후 폼 제출
    });
  });

  // 리뷰 수정 폼 제출 전에 리뷰가 입력되었는지 확인
  document.querySelector('#reviewForm').addEventListener('submit', function (event) {
    const selectedText = document.querySelector("textarea[name=content]").value;

    console.log("Selected Text:", selectedText);

    // 리뷰를 작성하지 않았을 경우
    if (selectedText === "") {
      alert("리뷰를 작성해주세요")
      event.preventDefault(); // 폼 제출을 막음
    }
  });
  // 취소를 눌렀을 시 이전 페이지로 이동
  function cancelModification() {
    window.history.back();
  }
</script>
</body>
</html>
