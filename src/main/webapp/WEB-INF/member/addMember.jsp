<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2023-09-24
  Time: 오후 3:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>

<body>

    <form action="/addMember" method="post">
        <p>이메일<input type="email" name="emailId"></p><span class="memberEmailCheck"></span>
        <p>비번<input type="password" name="password"></p>
        <p>비번확인<input type="password" name="password2"></p><span class="passCheck"></span>
       <p>이름 <input type="text" name="memberName"></p>
        <p>전화번호 <input type="text" name="phone"></p>
        <p> 생일 <input type="date" name="birthday"></p>
        <p> 우편번호 <input type="text" name="zipCode" id="zipCode" placeholder="우편번호" ></p><input type="button" name="findCode" value="우편번호 찾기"><br></p>
        <p> 번지수/도로명 <input type="text" name="address01" id="address01" placeholder="주소"></p>
        <p> 상세주소 <input type="text" name="address02"  id="address02" placeholder="상세주소"></p>

        <button type="submit">회원가입</button>
    </form>
</body>
<script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
<script>
    document.querySelector('input[name="findCode"]').addEventListener('click', execDaumPostcode);

    /* 카카오 우편번호 검색 가이드 페이지 :  https://postcode.map.daum.net/guide */
    function execDaumPostcode() {
        /* 상황에 맞춰서 변경해야 하는 부분 */
        const zipcode = document.getElementById('zipCode');
        const address01 = document.getElementById('address01');
        const address02 = document.getElementById('address02');

        /* 수정없이 사용 하는 부분 */
        new daum.Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') {
                    // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;
                } else {
                    // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if (data.userSelectedType === 'R') {
                    //법정동명이 있을 경우 추가한다.
                    if (data.bname !== '') {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if (data.buildingName !== '') {
                        extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += extraAddr !== '' ? ' (' + extraAddr + ')' : '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                zipcode.value = data.zonecode; //5자리 새우편번호 사용
                address01.value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                address02.focus();
            },
        }).open();
    }

    document.addEventListener("DOMContentLoaded", function () {
        const p1 = document.querySelector('input[name="password"]');
        const p2 = document.querySelector('input[name="password2"]');
        const c1 = document.querySelector(".passCheck");
        const lowercaseRegex = /[a-z]/;
        const digitRegex = /[0-9]/;

        p1.addEventListener("keyup", function () {
            if (p1.value.length < 8) {
                c1.style.color = "red"
                c1.innerHTML = "비밀번호는 여덟자 이상이어야 합니다";
            } else if (!lowercaseRegex.test(p1.value) || !digitRegex.test(p1.value)) {
                c1.style.color = "red"
                c1.innerHTML = "비밀번호는 반드시 영문(소문자)와 숫자를 포함해야 합니다!";
            } else {
                c1.style.color = "green"
                c1.innerHTML = "비밀번호가 유효합니다.";
            }
        });



        const xhr = new XMLHttpRequest();
        const emailId = document.querySelector('input[name=emailId]');
        emailId.addEventListener('keyup',function () {
            const emailIdval = emailId.value;
            const memberEmailCheck = document.querySelector('.memberEmailCheck'); //결과문자열
            xhr.open('GET','./ajaxIdCheck.jsp?emailId='+emailIdval );
            xhr.send();
            xhr.onreadystatechange = () => {
                if(xhr.readyState !== XMLHttpRequest.DONE) return;

                if(xhr.status === 200) {
                    const json = JSON.parse(xhr.response);
                    if(json.result === 'true') {
                        memberEmailCheck.style.color = 'red';
                        memberEmailCheck.innerHTML = '동일한 아이디가 있습니다!';
                    }
                    else {
                        memberEmailCheck.style.color = 'gray';
                        memberEmailCheck.innerHTML = '동일한 아이디가 없습니다.'
                    }
                }
                else {
                    console.error('Error',xhr.status,xhr.statusText);
                }
            }
        });
    });
</script>
</html>
