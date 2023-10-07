<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-10-02
  Time: 오후 5:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body><form method="post" action="/modifyPassword" >
        <p>새비밀번호 <input type="password" name="password"  required ></p>
        <p>새비밀번호 확인 <input type="password" name="password2" required></p><span class="passCheck"></span>
        <p><input name="emailId"  value="${emailId}" hidden="hidden"></p>
        <button type="submit" value="수정">수정</button>
        </form>
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
                                    if (json.result === 'true') {
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
</body>
</html>
