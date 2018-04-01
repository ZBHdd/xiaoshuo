<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 7) %></c:set>
<c:set var="colorArray" value="${fn:split('#aeabd8,#ded2c2,#dadfe3,#f1f1f1,#d2a689,#dcdcdc,#d7e7f3', ',')}"/>
<c:set var="imgArray" value="${fn:split('A.jpg,L.jpg,dd.jpg,aa.jpg,bb.jpg,Y.jpg,D.jpg', ',')}"/>
<h1 style="text-align: center">U阅小说阅读网</h1>
<h6 style="text-align: right">
    <c:choose>
        <c:when test="${empty user}">
            <span><a href="/login">登录</a>   <a href="/register">注册</a></span>
        </c:when>
        <c:otherwise>
            <span>您好！   ${user.username}</span>
        </c:otherwise>
    </c:choose>
</h6>