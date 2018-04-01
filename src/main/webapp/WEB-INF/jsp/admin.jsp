<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="static.jsp" %>
    <link href="/css/index.css" rel="stylesheet">
    <title>U阅小说阅读网</title>
</head>
<body>
<div class="container">
    <%@include file="header.jsp" %>
    <div class="item" style="background-color: ${colorArray[random.nextInt(7)]}">
        <a href="/category/addCategory">
            <div class="icon"><img src="/images/${imgArray[random.nextInt(7)]}" alt=""></div>
        </a>
        <div class="itemName"><h1>添加类型</h1></div>
        <div class="itemDesc">添加小说类型</div>
    </div>
    <div class="item" style="background-color: ${colorArray[rand mod 7]}">
        <a href="/novel/addNovel">
            <div class="icon"><img src="/images/${imgArray[rand mod 7]}" alt=""></div>
        </a>
        <div class="itemName"><h1>添加小说</h1></div>
        <div class="itemDesc">添加一本小说</div>
    </div>
    <%--<div class="item" style="background-color: #f1f1f1">--%>
    <%--<a href="/image/otherImageView">--%>
    <%--<div class="icon"><img src="/images/icons/aa.jpg" alt=""></div>--%>
    <%--</a>--%>
    <%--<div class="itemName"><h1>图片浏览</h1></div>--%>
    <%--<div class="itemDesc">查看自己或者别人上传的图片</div>--%>
    <%--</div>--%>
    <%--<div class="item" style="background-color: #dcdcdc">--%>
    <%--<a href="/image/imagesUpload">--%>
    <%--<div class="icon"><img src="/images/icons/bb.jpg" alt=""></div>--%>
    <%--</a>--%>
    <%--<div class="itemName"><h1>上传图片</h1></div>--%>
    <%--<div class="itemDesc">可以上传各种类型的图片，最多5M，腾讯云50G流量</div>--%>
    <%--</div>--%>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

