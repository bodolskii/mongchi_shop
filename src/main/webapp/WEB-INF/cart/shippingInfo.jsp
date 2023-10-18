<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.mongchi_shop.dto.CartDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.mongchi_shop.dto.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	List<CartDTO> cartDTOList = (List<CartDTO>) session.getAttribute("cartDTOList");
	MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
	String orderId = (String) session.getAttribute("orderId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 정보</title>
</head>
<body>
	<!-- Navigation Bar -->
	<jsp:include page="/WEB-INF/inc/menu.jsp" />

	<!-- Start Hero Section -->
	<div class="hero">
		<div class="container">
			<div class="row justify-content-between">
				<div class="col-lg-5">
					<div class="intro-excerpt">
						<h1>주문 정보</h1>
					</div>
				</div>
				<div class="col-lg-7">

				</div>
			</div>
		</div>
	</div>
	<!-- End Hero Section -->

	<div class="untree_co-section">
		<div class="container">
			<form action="/cart/shippingInfo" method="post">
				<div class="row">
					<div class="col-md-6 mb-5 mb-md-0">
						<h2 class="h3 mb-3 text-black">배송지 정보</h2>
						<div class="p-3 p-lg-5 border bg-white">
							<div class="form-group row">
								<div class="col-md-12">
									<label for="orderName" class="text-black">주문자 이름 <span class="text-danger">*</span></label>
									<input type="text" class="form-control" id="orderName" name="orderName" value="<%= memberDTO.getMemberName() %>">
								</div>
							</div>

							<div class="form-group row">
								<div class="col-md-6">
									<label for="zipCode" class="text-black">우편번호 <span class="text-danger">*</span></label>
									<input type="text" class="form-control" id="zipCode" name="zipCode" value="<%= memberDTO.getZipCode() != null ? memberDTO.getZipCode() : "" %>" readonly>
								</div>
								<div class="col-md-6">
									<button class="btn btn-secondary btnFindZipcode" type="button" style="cursor:pointer; margin-top: 28px;">우편번호검색</button>
								</div>
							</div>

							<div class="form-group row">
								<div class="col-md-12">
									<label for="address01" class="text-black">주소 <span class="text-danger">*</span></label>
									<input type="text" class="form-control" id="address01" name="address01"
										   value="<%= memberDTO.getAddress01() != null ? memberDTO.getAddress01() : "" %>" placeholder="" readonly>
								</div>
							</div>

							<div class="form-group mt-3">
								<input type="text" class="form-control" name="address02"
									   value="<%= memberDTO.getAddress02() != null ? memberDTO.getAddress02() : "" %>" placeholder="상세주소 입력">
							</div>

							<div class="form-group row mb-5">
								<div class="col-md-12">
									<label for="phone" class="text-black">연락처 <span class="text-danger">*</span></label>
									<input type="text" class="form-control" id="phone" name="phone"
										   value="<%= memberDTO.getPhone() %>" placeholder="ex) 010-1234-5678">
								</div>
							</div>

							<div class="form-group">
								<label for="order_notes" class="text-black">Order Notes</label>
								<textarea name="order_notes" id="order_notes" cols="30" rows="5" class="form-control" placeholder="Write your notes here..."></textarea>
							</div>

						</div>
					</div>

					<div class="col-md-6">

						<div class="row mb-5">
							<div class="col-md-12">
								<h2 class="h3 mb-3 text-black">주문 정보</h2>
								<div class="p-3 p-lg-5 border bg-white">
									<table class="table site-block-order-table mb-5">
										<thead>
											<th>Product</th>
											<th>Total</th>
										</thead>
										<tbody>
											<%
												int totalAmount = 0;
												for (CartDTO cart : cartDTOList) {;
													int price = cart.getUnitPrice() * cart.getCnt();
													totalAmount += price;
											%>
											<tr>
												<td>
													<%= cart.getProductName() %> <strong class="mx-2">x</strong> <%= cart.getCnt() %>
												</td>
												<td><%= price %>원</td>
											</tr>
											<%
												}
											%>
										<tr>
											<td class="text-black font-weight-bold"><strong>Order Total</strong></td>
											<td class="text-black font-weight-bold"><strong><%= totalAmount %>원</strong></td>
										</tr>
										</tbody>
									</table>

									<div class="form-group">
										<button class="btn btn-black btn-lg py-3 btn-block" type="submit">주문하기</button>
									</div>

									<input type="hidden" name="orderId" value="<%= orderId %>">
									<input type="hidden" name="emailId" value="<%= memberDTO.getEmailId() %>">
									<input type="hidden" name="totalAmount" value="<%= totalAmount %>">
									<input type="hidden" name="orderStatus" value="주문완료">

								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- 다음 주소 검색 api -->
	<script>
        document.addEventListener('DOMContentLoaded', function () {
            const btnFindZipcode = document.querySelector('.btnFindZipcode');
            btnFindZipcode.addEventListener('click', execDaumPostcode);
        });
    </script>

    <script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
    <script>
        /* 
        카카오 우편번호 검색 가이드 페이지 :  https://postcode.map.daum.net/guide
        */
        function execDaumPostcode() {
            /* 상황에 맞춰서 변경해야 하는 부분 */
            const zipcode = document.querySelector("input[name=zipCode]");
            const address01 = document.querySelector("input[name=address01]");
            const address02 = document.querySelector("input[name=address02]");

            /* 수정없이 사용 하는 부분 */
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var fullAddr = ''; // 최종 주소 변수
                    var extraAddr = ''; // 조합형 주소 변수

                    // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        fullAddr = data.roadAddress;
                    }
                    else { // 사용자가 지번 주소를 선택했을 경우(J)
                        fullAddr = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                    if(data.userSelectedType === 'R'){
                        //법정동명이 있을 경우 추가한다.
                        if(data.bname !== ''){
                            extraAddr += data.bname;
                        }
                        // 건물명이 있을 경우 추가한다.
                        if(data.buildingName !== ''){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                        fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    zipcode.value = data.zonecode; //5자리 새우편번호 사용
                    address01.value = fullAddr;

                    // 커서를 상세주소 필드로 이동한다.
                    address02.focus();
                }
            }).open();
        }
    </script> 
</body>
</html>