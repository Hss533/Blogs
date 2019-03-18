<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/6/25
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.hss.util.CryptographyUtil"%>%>
<html>
<head>
    <title>hss个人博客--后台管理界面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        var url;
        $(function () {
            $(".easyui-accordion").accordion('getSelected').panel('collapse');
        })
        function openTab(text,url,iconCls)
        {
            if($("#tabs").tabs("exists",text))
            {
                $("#tabs").tabs("select",text);
                return ;
            }
            var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/admin/"+url+"'></iframe>";
            $("#tabs").tabs("add",{
                title:text,
                iconCls:iconCls,
                closable:true,
                content:content
            });

        }
        function openPasswordModifyDialog()
        {
            var oldPassword = $("#oldPassword").val();
            $("#dlg").dialog("open").dialog("setTitle","修改用户密码");
            url="${pageContext.request.contextPath}/admin/blogger/modifyPassword.do";
        }
        function modifyPassword()
        {
            $("#fm").form("submit",{
                url:url,
                onSubmit:function()
                {
                    var newPassword = $("#newPassword").val();
                    var newPassword2 = $("#newPassword2").val();

                    if(!$(this).form("validate"))
                    {
                        return false;
                    }

                    if(newPassword != newPassword2)
                    {
                        $.messager.alert("系统提示","新密码和确认密码不同!");
                        return false;
                    }
                    return true;
                },
                success:function(result)
                {
                    var result = eval('('+result+')');
                    if(result.success)
                    {
                        $.messager.alert("系统提示","密码修改成功，下次登录生效");
                        resetValue();
                        $("#dlg").dialog("close");
                    }
                    else
                    {
                        $.messager.alert("系统提示","修改失败!");
                        return ;
                    }
                }
            });
        }
        function refreshSystem()
        {
            $.post("${pageContext.request.contextPath}/admin/system/refreshSystem.do",{},
                function(result)
                {
                    if(result.success)
                    {
                        $.messager.alert("系统提示","刷新成功");
                    }
                    else
                    {
                        $.messager.alert("系统提示","刷新失败");
                    }
                },"json");
        }

        function closemodifyPasswordDialog()
        {
            $("#dlg").dialog("close");
            resetValue();
        }

        function resetValue()
        {
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        function logout()
        {
            $.messager.confirm("系统提示","您确定要退出系统吗?",function(r){
                if(r)
                {
                    window.location.href="${pageContext.request.contextPath}/admin/blogger/logout.do";
                }
            });
        }

    </script>
</head>
<body class="easyui-layout">
    <div region="north"	style="height:90px;background-color:#E0ECFF;">
        <table width="100%">
            <tr>
                <td width="50%">
                    <div style="padding:1px 25px;">
                        <p  style="font-size: 25px;font-weight: bold">胡白白博客管理系统</p>
                    </div>
                </td>
                <td valign="bottom" align="right" width="50%">
                    <div style="padding-right: 15px;">
                        <span style="font-size: 18px;font-weight: bold" >
                            &nbsp;&nbsp;管理员
                                <c:if test="${currentManager.power == 1 }">
                                    系统管理员：
                                </c:if>
                                <c:if test="${currentManager.power == 2 }">
                                    售票员：
                                </c:if>
                            ${blogger.userName }</span>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div region="center">
        <div class="easyui-tabs" fit="true" border="false" id="tabs">
            <div title="首页" data-options="iconCls:'icon-home'">
                <div align="center" style="padding-top:100px">
                    <span style="color: red;font-size: 30px">欢迎使用</span>
                </div>
            </div>
        </div>
    </div>
    <div region="west" style="width:200px" title="导航菜单" split="true">

            <div class="easyui-accordion" data-options="fit:true,border:false">
                <div title="常用操作" data-options="iconCls:'icon-item'" style="padding: 10px">
                    <a href="javascript:openTab('写博客','writeBlog.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px;">写博客</a>
                    <a href="javascript:openTab('博客管理','blogManager.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px">博客管理</a>
                     <a href="javascript:refreshSystem()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
                    <a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
                </div>
                <div title="博客管理" data-options="iconCls:'icon-yxgl'" style="padding:10px">
                    <a href="javascript:openTab('写博客','writeBlog.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px;">写博客</a>
                    <a href="javascript:openTab('博客管理','blogManager.jsp','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">博客管理</a>
                </div>
                <div title="博客类别管理"  data-options="iconCls:'icon-khgl'" style="padding:10px;">
                    <a href="javascript:openTab('博客类别管理','blogTypeManager.jsp','icon-yxjhgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-yxjhgl'" style="width: 150px;">博客类别管理</a>
                </div>
                <div title="评论管理"  data-options="iconCls:'icon-khgl'" style="padding:10px;">
                    <a href="javascript:openTab('未审核评论管理','commentReview.jsp','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">未审核评论管理</a>
                    <a href="javascript:openTab('评论管理','commentManage.jsp','icon-khkfjh')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-khkfjh'" style="width: 150px;">评论管理</a>
                 </div>
                <div title="标签管理"  data-options="iconCls:'icon-tag'" style="padding:10px">
                    <a href="javascript:openTab('友情链接管理','LinkManage.jsp','icon-notice')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-notice'" style="width: 150px;">友情链接管理</a>
                </div>
                <div title="个人管理"  data-options="iconCls:'icon-tag'" style="padding:10px">
                    <a href="javascript:openTab('个人信息管理','modifyInfo.jsp','icon-notice')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-notice'" style="width: 150px;">个人信息</a>
                </div>
                <div title="系统管理"  data-options="iconCls:'icon-item'" style="padding:10px">
                    <a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
                    <a href="javascript:refreshSystem()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
                    <a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
                </div>
            </div>L
    </div>

    <div region="south" style="height:30px; padding:5px" align="center">

    </div>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:250px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="8px">

                <tr>
                    <td>用户名:</td>
                    <td><input type="text" id="account" name="account" value="${currentUser.userName }" readonly="readonly" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>原密码:</td>
                    <td><input type="password" id="oldPassword" name="oldPassword" class="easyui-validatebox" required="true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>新密码:</td>
                    <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox" required="true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>确认新密码:</td>
                    <td><input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox" required="true" style="width:200px;"></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
        <a href="javascript:closemodifyPasswordDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
    </div>

</body>
</html>
