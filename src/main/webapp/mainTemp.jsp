<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
    <title>博主主页</title>
    <style type="text/css">
        body{
            padding-top: 10px;
            padding-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="container">

   <jsp:include page="foreground/commom/head.jsp"/>

    <jsp:include page="foreground/commom/menu.jsp"/>



    <div class="row">
        <div class="col-md-9">
                <jsp:include page="${mainPage}"/>
        </div>
        <div >
        <jsp:include page="foreground/commom/rigth.jsp"/>
        </div>
    </div>

    <jsp:include page="foreground/commom/foot.jsp"/>

</div>
</body>
</html>