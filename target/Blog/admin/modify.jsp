<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/9/22
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新博客</title>
    <!--添加easyUI框架-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <!--加载百度ueditor编辑器-->
    <script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        function submitData()
        {
            var id=$("#id").val();
            var title=$("#title").val();
            var blogTypeId=$("#blogTypeId").combobox("getValue");
            var content=UE.getEditor('editor').getContent();
            var keyWord=$("#keyWord").val();//关键字
            //摘要

            if(title==null||title=='')
            {
                alert("请输入标题")
            }else  if(blogTypeId==null|| blogTypeId=='')
            {
                alert("请选择博客类别");
            }else if(content==null|| content=='')
            {
                alert("请填写内容")
            }
            else {
                $.post("${pageContext.request.contextPath}/admin/blog/save.do",{'id':id,'title':title,'blogType.id':blogTypeId,'content':content,'contentNoTag':UE.getEditor('editor').getContentTxt(),'summary':UE.getEditor('editor').getContentTxt().substring(0,155),'keyWord':keyWord},
                function(result)
                {
                    if(result.success)
                    {
                        alert("博客发布成功");
                        resultValue();
                    }
                    else
                    {
                        alert("博客发布失败");
                    }
                },"json"
                );
            }
        }
        //提交完成之后
        function resultValue()
        {
            $("#title").val("");
            $("#blogTypeId").combobox("setValue","");
            UE.getEditor('editor').setContent("");
            $("#keyWord").val("");
        }
    </script>
</head>

<body style="margin: 10px;">
<div id="p" class="easyui-panel" title="编写博客" style="padding: 10px;width: 1300px;margin-right: 20px;">
<table cellpadding="20px;">
    <input type="hidden" id="id" name="id" style="width:400px"/>
    <tr>
        <td width="80px">博客名称:</td>
        <td><input type="text" id="title" name="title" style="width: 400px"></td>
    </tr>
    <tr>
        <td>
            所属类别:
        </td>
        <td>
            <select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="blogType.id" editable="false" panelHeight="auto">
                <option value="">请选择博客类别：</option>
                <c:forEach var="blogType" items="${blogTypeList}">
                    <option value="${blogType.id}">${blogType.typeName}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
        <tr>
            <td valign="top">博客内容</td>
            <td style="width: 1100px;" >
                <script id="editor" name="editor" type="text/plain"></script>
                <input type="hidden" id="content" name="content"  style="height: 1000px"/></td>
        </tr>
        <tr>
            <td>关键字:</td>
            <td>
                <input type="text" id="keyWord" name="keyWord" style="width:400px">(其中以空格隔开)
            </td>
        </tr>
        <tr>
            <td>发布博客:</td>
            <td ><a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'" >发布博客</a></td>
        </tr>


</table>
</div>
    <script type="text/javascript">
        var ue = UE.getEditor('editor');

        ue.addListener("ready",function(){
            // 通过ajax请求数据
            UE.ajax.request("${pageContext.request.contextPath}/admin/blog/findById.do",
                {
                    method:"post",
                    async:false,
                    data:{"id":"${param.id}"},
                    onsuccess:function(result){
                        result=eval("("+result.responseText+")");
                        $("#id").val(result.id);
                        $("#title").val(result.title);
                        $("#keyWord").val(result.keyWord);
                        $("#blogTypeId").combobox("setValue",result.blogType.id);
                        UE.getEditor('editor').setContent(result.content);
                    }
                });
        });
    </script>
</body>
</html>
