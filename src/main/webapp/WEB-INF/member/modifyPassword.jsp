<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>비밀번호 수정</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<body>
<jsp:include page="/WEB-INF/inc/menu.jsp"/>
<div class="container">
        <form method="post" action="/member/modifyPassword">
                <div class="form-group">
                        <label >새 비밀번호</label>
                        <input type="password" name="password" id="password" class="form-control" required style="width: 40%">
                </div>
                <div class="form-group">
                        <label >새 비밀번호 확인</label>
                        <input type="password" name="password2" class="form-control" id="password2" required style="width: 40%">
                        <span class="passCheck"></span>
                </div>
                <input name="emailId" value="${emailId}" hidden="hidden" id="emailId">
                <button type="submit" class="btn btn-primary">수정</button>
        </form>
</div>

<script>
        document.addEventListener("DOMContentLoaded",function () {
                const p1 = document.querySelector('input[name="password"]');
                const p2 = document.querySelector('input[name="password2"]');
                const e1 = document.querySelector('input[name="emailId"]');
                const c1 = document.querySelector(".passCheck");
                const lowercaseRegex = /[a-z]/;
                const digitRegex = /[0-9]/;

                p1.addEventListener("keyup", function () {


                        if (p1.value.length < 8) {
                                c1.style.color = "red";
                                c1.innerHTML = "비밀번호는 여덟자 이상이어야 합니다";
                        } else if (!lowercaseRegex.test(p1.value) || !digitRegex.test(p1.value)) {
                                c1.style.color = "red";
                                c1.innerHTML = "비밀번호는 반드시 영문(소문자)와 숫자를 포함해야 합니다!";
                        } else {
                                c1.style.color = "green";
                                c1.innerHTML = "비밀번호가 유효합니다.";
                        }
                });
                p1.addEventListener("focusout", function () {
                        const xhr = new XMLHttpRequest();

                        xhr.open('GET', './passwordCheck.jsp?emailId=' + e1.value+"&password="+p1.value);
                        xhr.send();
                        xhr.onreadystatechange = () => {
                                if (xhr.readyState !== XMLHttpRequest.DONE) return;

                                if (xhr.status === 200) {
                                        const json = JSON.parse(xhr.response);
                                        console.log(json)
                                        if (json.result === 'true') {
                                                console.log("200 TRUE")
                                                alert("기존비밀번호와 동일한번호입니다");
                                                p1.clear;
                                        } else {
                                                console.error('Error', xhr.status, xhr.statusText);
                                        }

                                }
                        }
                })
                p2.addEventListener("focusout", function () {
                        if (p1.value === "") {
                                p1.focus();
                                c1.style.color = "red"
                                c1.innerHTML = "비밀번호를 입력해 주세요";
                        } else if (p1.value !== p2.value) {
                                c1.style.color = "red"
                                c1.innerHTML = "비밀번호가 일치하지 않습니다!";
                                p1.value = "";
                                p2.value = "";
                        } else if (p1.value === p2.value) {
                                c1.style.color = "green";
                                c1.innerHTML = "비밀번호가 일치합니다!";
                        }
                });
        });



</script>
<jsp:include page="/WEB-INF/inc/footer.jsp" />
</body>
</html>
