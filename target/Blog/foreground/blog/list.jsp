<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/9/13
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!--这个就是中间的那一块，存放动态数据用的，-->
<div class="data_list">
    <div class="data_list_title">
    <img src="${pageContext.request.contextPath}/static/images/list_icon.png"/>
    ${tiny_title}
</div>
    <div class="datas">
        <ul>

            <c:forEach var="list" items="${blogList}">
                <li style="margin-bottom: 30px">

                <span class="date"><a href="${pageContext.request.contextPath}/blog/articles/${list.id}.html${params}"><fmt:formatDate value="${list.releaseDate}" type="Date" pattern="yyyy年MM月dd日"/></a></span>
                <span class="title"><a href="${pageContext.request.contextPath}/blog/articles/${list.id}.html${params}">${list.title}</a></span>
                <span class="summary">${list.summary}...</span>
                <span class="img">
             </span>
                <span class="info">发表于 <fmt:formatDate value="${list.releaseDate}" type="Date" pattern="yyyy-MM-dd HH:mm"/> 阅读(${list.clickHit}) 评论(${list.replyHit}) </span>

                </li>
                <hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
            </c:forEach>
        </ul>
    </div>
</div>
<div>
    <nav>
        <ul class="pagination pagination-sm">
            ${pageCode}
        </ul>
    </nav>
</div>