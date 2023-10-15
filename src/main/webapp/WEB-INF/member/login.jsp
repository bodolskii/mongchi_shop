<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="/WEB-INF/inc/menu.jsp" />
<div class="container">
    <c:if test="${param.result == 'error'}">
        <div class="alert alert-danger">
            아이디와 비밀번호를 확인해 주세요
        </div>
    </c:if>
    <form action="/login" method="post">
        <div class="form-group">
            <label>이메일</label>
            <input type="email" name="emailId" class="form-control">
        </div>
        <div class="form-group">
            <label>비번</label>
            <input type="password" name="password" class="form-control">
        </div>
        <div class="form-check">
            <input type="checkbox" name="auto" class="form-check-input">
            <label class="form-check-label">자동 로그인</label>
        </div>
        <button type="submit" class="btn btn-primary">로그인</button>
        <a href="/addMember" class="btn btn-link">회원가입</a>
    </form>
</div>
<jsp:include page="/WEB-INF/inc/footer.jsp" />
</body>
</html>