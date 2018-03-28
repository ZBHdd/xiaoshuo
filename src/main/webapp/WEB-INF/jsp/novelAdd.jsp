<!DOCTYPE html>
<html lang="zh-CN">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <%@include file="static.jsp" %>
    <title>添加小说类型</title>
    <link href="/css/register.css" rel="stylesheet">
</head>
<body>
<div class="topInfo">
    <label class="title">添加小说类型</label>
    <img src="/images/icon.jpg" alt="">
</div>
<%-- TODO 验证码错误时，给予提示--%>
<%-- TODO 注册出现错误后， 保留用户名--%>
<div class="reg_bg">
    <form method="post" action="/category/addNovel" enctype="multipart/form-data">
        <div class="input_line">
            <label class="input_label">类别：
                <select class="form-control input_text" id="pid" name="pid">
                </select>
            </label>
        </div>
        <div class="input_line">
            <label class="input_label">名称：</label>
            <input class="form-control input_text " type="text" name="name" id="name" placeholder="小说名称">
        </div>
        <div class="input_line">
            <label class="input_label">描述：</label>
            <input class="form-control  input_text" type="text" name="remark" id="remark" placeholder="类型描述"/>
        </div>
        <div class="input_line">
            <label class="input_label">图片：</label>
            <input class="form-control  input_text" type="file" style="display: inline-flex"
                   name="img" id="img" placeholder="请上传图片" required/>
        </div>
        <div class="input_line">
            <label class="input_label">小说：</label>
            <input class="form-control  input_text" type="file" style="display: inline-flex"
                   name="img" id="imgNovel" placeholder="请上传小说" required/>
        </div>
        <div class="">
            <label class="input_label"></label>
            <input class="btn btn-primary btn-lg input_submit" type="submit" value="添加">
        </div>
    </form>
</div>
<%@include file="footer.jsp" %>
</body>
<script>
    $.get("/category/list",function (data) {

    })
</script>
</html>