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
    <c:if test="${showType == 0}&& categorys.length>0">
        <c:forEach var="category" items="categorys" varStatus="category">
            <div class="item" style="background-color: ${colorArray[rand mod 7]}">
                <a href="/novel/find?id=${category.id}&pageSize=8">
                    <div class="icon"><img src="/images/${imgArray[rand mod 7]}" alt=""></div>
                </a>
                <div class="itemName"><h1>${category.name}</h1></div>
                <div class="itemDesc">${category.remark}</div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${showType == 3}">
        <ul id="selector" class="pagination-sm"></ul>
        <div id="show"></div>
    </c:if>
</div>
<%@include file="footer.jsp" %>
</body>
</html>

<script>
    $('#selector').pagination({
        items: pageSum,
        itemOnPage: itemOnPage,
        prevText: '<span aria-hidden="true">&laquo;</span>',
        nextText: '<span aria-hidden="true">&raquo;</span>',
        onPageClick: function (page, evt) {
            $.post("/novel/listPage", {page: page, pageSize: pageSize}, function (data) {
                if (data) {
                    $('#show').html('');
                    for (i in data) {
                        $('#show').append(" <div class=\"item\" style=\"background-color: ${colorArray[rand mod 7]}\">\n" +
                            "                <a href=\"/novel/see?id="+data[i].id+">\n" +
                            "                    <div class=\"icon\"><img src="+data[i].img+" alt=\"\"></div>\n" +
                            "                </a>\n" +
                            "                <div class=\"itemName\"><h1>"+data[i].name+"</h1></div>\n" +
                            "                <div class=\"itemDesc\">"+data[i].remark+"</div>\n" +
                            "            </div>")
                    }
                }
            }, "json")
        }
    });
</script>