<!--这个页面是博客详情页面-->
<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/9/19
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%--百度编辑器，使用之后产生高亮--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
    SyntaxHighlighter.all();

    //超过10条评论的时候进行显示
    function showOtherComment(){
        $(".otherComment").show();
    }

    //更换验证码图片
    function loadImage(){
        document.getElementById("randImage").src="${pageContext.request.contextPath}/image.jsp?"+Math.random();
    }

    function submitData()
    {
        var content=$("#content").val();
        var imageCode=$("#imageCode").val();
        if(content==null || content=="")
        {
            alert("请输入评论内容！");
        }else if(imageCode==null || imageCode=="")
        {
            alert("请填写验证码！");
        }else{
            $.post("${pageContext.request.contextPath}/comment/add.html",{"content":content,'imageCode':imageCode,'blog.id':'${blog.id}'},
                function(result)
                {
                    if(result.success)
                    {
                        alert("评论已提成成功，审核通过后显示！");

                        //提交完成后进行刷新页面
                        location.reload();
                    }
                    else
                    {
                        alert(result.errorInfo);
                    }
            },"json");
        }
    }
    //点赞
    function likeButton()
    {
        var like=$("#like").val();
        var status=0;
        if(like=="like")
        {
            status=0;
        }
        //表示取消点赞
        if(like=="unlike")
        {
            status=1;
        }
        $.post("${pageContext.request.contextPath}/blog/likeOrUnlike.do",{"id":${blog.id},"status":status},
            function(result)
            {
                if(result.success)
                {
                    alert(result.successInfo);
                    location.reload();
                }
                else
                {
                    alert(result.errorInfo);
                }
            },"json");
    }

</script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/blog_show_icon.png"/>
        博客详情
    </div>
    <div class="datas" style="padding: 10px;">
        <div class="blog_title">
            <h3>
                <strong>${blog.title}</strong>
            </h3>
        </div>
        <div style="padding-left: 315px;padding-bottom: 20px;padding-top: 10px">
            <div class="bshare-custom">
                分享到:
                <a href="#"><img src="${pageContext.request.contextPath}/static/share/QQ.png"></a>
                <a href="#"><img src="${pageContext.request.contextPath}/static/share/weiChat.png"></a>
                <a href="#"><img src="${pageContext.request.contextPath}/static/share/weiBo.png"></a>
                <a href="#"><img src="${pageContext.request.contextPath}/static/share/bilibilli.png"></a>
            </div>
        </div>
        <div class="blog_info">
            发布时间:[<fmt:formatDate value="${blog.releaseDate}" pattern="yyyy-MM-dd HH:mm" type="date"/>]&nbsp;&nbsp;博客类别：${blog.blogType.typeName } &nbsp;&nbsp;阅读量(${blog.clickHit})
        </div>
        <div class="blog_content">
            ${blog.content}
        </div>
        <div class="blog_keyWord" style="float: right;">
            <span style="font-family: '楷体' ;font-weight: bold"><i>关键字:</i></span>
            <c:forEach items="${keyWordList}" var="list">
                <!--这个链接是进行搜索-->
                &nbsp;&nbsp;<a href="${pageContext.request.contextPath}/blog/q.html?q=${list}" target="_blank" style="font-family:' 楷体'"><i>${list}</i></a>&nbsp;&nbsp;
            </c:forEach>
        </div>
        <div class="like">
            <input type="button" value="${like}" id="like" name="like" onclick="likeButton()">
        </div>
        <div class="blog_lastAndNextPage" style="width: 100%" >
            <c:if test="${typeId!=null}">
                <c:if test="${nextBlog.id!=null}">
                    <p>下一篇：<a href="${pageContext.request.contextPath}/blog/articles/${nextBlog.id}.html?typeId=${typeId}">${nextBlog.title}</a></p>
                </c:if>
                <c:if test="${nextBlog.id==null}">
                    <p>下一篇：${nextBlog.title}</p>
                </c:if>

                <c:if test="${preBlog.id!=null}">
                    <p>上一篇：<a href="${pageContext.request.contextPath}/blog/articles/${preBlog.id}.html?typeId=${typeId}">${preBlog.title}</a></p>
                </c:if>
                <c:if test="${preBlog.id==null}">
                    <p>上一篇：${preBlog.title}</p>
                </c:if>
            </c:if>
            <c:if test="${typeId==null}">
                    <c:if test="${releaseDate!=null}">
                            <c:if test="${nextBlog.id!=null}">
                                <p>下一篇123：<a href="${pageContext.request.contextPath}/blog/articles/${nextBlog.id}.html?releaseDateStr=${releaseDate}">${nextBlog.title}</a></p>
                            </c:if>
                            <c:if test="${nextBlog.id==null}">
                                <p>下一篇1：${nextBlog.title}</p>
                            </c:if>
                            <c:if test="${preBlog.id!=null}">
                                <p>上一篇：<a href="${pageContext.request.contextPath}/blog/articles/${preBlog.id}.html?releaseDateStr=${releaseDate}">${preBlog.title}</a></p>
                            </c:if>
                            <c:if test="${preBlog.id==null}">
                                <p>上一篇：${preBlog.title}</p>
                            </c:if>
                    </c:if>

                    <c:if test="${releaseDate==null}">

                        <c:if test="${nextBlog.id!=null}">
                            <p>下一篇：<a href="${pageContext.request.contextPath}/blog/articles/${nextBlog.id}.html">${nextBlog.title}</a></p>
                        </c:if>
                        <c:if test="${nextBlog.id==null}">
                            <p>下一篇：${nextBlog.title}</p>
                        </c:if>

                        <c:if test="${preBlog.id!=null}">
                            <p>上一篇：<a href="${pageContext.request.contextPath}/blog/articles/${preBlog.id}.html">${preBlog.title}</a></p>
                        </c:if>
                        <c:if test="${preBlog.id==null}">
                            <p>上一篇：${preBlog.title}</p>
                        </c:if>

                    </c:if>
            </c:if>
        </div>
    </div>
    <!--评论展示-->
    <div style="padding: 10px;">
        <!--在评论中超过十条的评论要进行折叠-->
        <div style="border-bottom: solid 3px #D81E06;padding-bottom: 5px">
            <span style="font-weight: bold;font-size: 18px">评论</span></div>
        <div>
            <c:choose>
                <c:when test="${commentListLength==0}">
                    暂无评论
                </c:when>
                <c:otherwise>
                    <c:forEach var="list" items="${commentList}" varStatus="status" >
                        <div style="background-color:#f6bfbc;margin: 5px; padding: 5px;border-radius: 10px;">
                            <span style="font-weight: bold;font-size: 16px;">${commentListLength-status.index}楼:</span>&nbsp;&nbsp;&nbsp;&nbsp;${list.content}
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <!--添加评论-->
    <div class="addComment" >
        <div class="addCommentTitle" style="padding-bottom: 5px;padding-top: 10px">
            <span style=" font-weight: bold;font-size: 18px">&nbsp; 发表评论</span>
        </div>
        <div class="addCommentContent">
            <div class="addCommetnTextArea" style="margin: 10px">
                <textarea style="width: 100%;" rows="3" id="content" placeholder="你有什么感想？"></textarea>
             </div>
            <div class="addCommentVerCode">
                &nbsp;&nbsp;验证码：
                <!--表示用户按下enter键时触发form提交,13是keyCode键的编码-->
                <input type="text" name="imageCode"  id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>
                &nbsp;
                <img onclick="javascript:loadImage();"  title="换一张试试" name="randImage" id="randImage" src="/image.jsp" width="60" height="20" border="1" align="absmiddle">
            </div>
            <div class="publishButton" style="margin: 10px">
                <button class="btn-primary  btn" type="reset" onclick="submitData()">发表评论</button>
            </div>
        </div>
    </div>
</div>