<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/9/13
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-3" style="float: right">
    <div class="data_list">
        <div class="data_list_title">
            <img src="${pageContext.request.contextPath}/static/images/user_icon.png"/>
            博主信息
        </div>
        <div class="user_image">
            <img src="${pageContext.request.contextPath}/static/userImages/${blogger.imageName}" />
        </div>
        <div class="nickName">${blogger.nickName}</div>
        <div class="userSign">${blogger.sign}</div>
    </div>

    <div class="data_list">
        <div class="data_list_title">
            <img src="${pageContext.request.contextPath}/static/images/byType_icon.png"/>
            按日志类别
        </div>
        <div class="datas">
            <ul>

                <c:forEach var="list" items="${blogTypeList}">
                    <li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${list.id}">${list.typeName}(${list.blogCount})</a></span></li>
                </c:forEach>

            </ul>
        </div>
    </div>



    <div class="data_list">
        <div class="data_list_title">
            <img src="${pageContext.request.contextPath}/static/images/byDate_icon.png"/>
            按日志日期
        </div>
        <div class="datas">
            <ul>
                <c:forEach var="list" items="${dateList}" >
                    <li>
                        <span>
                            <a href="${pageContext.request.contextPath}/index.html?releaseDateStr=${list.releaseDateStr}" target="_blank">
                                    ${list.releaseDateStr}(${list.blogCount})
                            </a>
                        </span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="data_list">
        <div class="data_list_title">
            <img src="${pageContext.request.contextPath}/static/images/link_icon.png"/>
            友情链接
        </div>
        <div class="datas">
            <ul>
                <c:forEach items="${linkList}" var="link">
                    <li><span><a href="${link.linkUrl}" target="_blank">${link.linkName}</a></span></li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>