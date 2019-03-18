<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/9/13
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">

    function search(){
        var q=document.getElementById("q").value.trim();
        if(q==null || q==""){
            alert("请输入您要查询的关键字！");
            return false;
        }else{
            return true;
        }
    }
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12" style="padding-top: 10px">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/index.html"><span style="color: black;font-weight: bold">首页</span></a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/blogger/aboutMe.html"><span style="color: black" ><strong>关于博主</strong></span></a></li>
                    </ul>
                    <form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/blog/q.html"
                           method="post" onsubmit="return search()">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Searching..."  id="q" name="q" value="${q}">
                        </div>
                        <button type="submit" class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>
        </nav>
    </div>
</div>