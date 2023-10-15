<%@ page import="com.example.mongchi_shop.dto.MemberDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link href="/css/tiny-slider.css" rel="stylesheet">
<link href="/css/style.css" rel="stylesheet">

<%--<script src="https://kit.fontawesome.com/dc88001a08.js" crossorigin="anonymous"></script>--%>

<%
    MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
    String emailId = null;
    String memberName = null;
    if (memberDTO != null) {
        emailId = memberDTO.getEmailId();
        memberName = memberDTO.getMemberName();
    }
%>

<!-- Start Header/Navigation -->
<nav class="custom-navbar navbar navbar navbar-expand-md navbar-dark bg-dark" arial-label="Furni navigation bar">

    <div class="container">
        <a class="navbar-brand" href="/">Mongchi Shop<span>.</span></a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsFurni" aria-controls="navbarsFurni" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsFurni">
            <ul class="custom-navbar-nav navbar-nav ms-auto mb-2 mb-md-0">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li><a class="nav-link" href="/products">Product</a></li>
                <li><a class="nav-link" href="about.html">About us</a></li>
                <li><a class="nav-link" href="services.html">Services</a></li>
                <li><a class="nav-link" href="blog.html">Blog</a></li>
                <li><a class="nav-link" href="contact.html">Contact us</a></li>
            </ul>

            <ul class="custom-navbar-cta navbar-nav mb-2 mb-md-0 ms-5">
                <c:set var="emailId" value="<%= emailId %>"/>
                <c:set var="memberName" value="<%= memberName %>"/>
                <c:choose>
                    <c:when test="${empty emailId }">
                        <li><a class="nav-link" href="/login">LogIn</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a class="nav-link">[${memberName}&nbsp;님]</a></li>
                        <li><a class="nav-link" href="/logout">LogOut</a></li>
                    </c:otherwise>
                </c:choose>
                <li><a class="nav-link" href="#"><img src="/images/user.svg"></a></li>
                <li><a class="nav-link" href="/cart/list"><img src="/images/cart.svg"></a></li>
            </ul>
        </div>
    </div>

</nav>
<!-- End Header/Navigation -->
