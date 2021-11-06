<%--
  Created by IntelliJ IDEA.
  User: yjiewei
  Date: 2021/11/6
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="500.jsp"
%>
<html>
<head>
    <title>学习JSP</title>
</head>
<body>
    <div>
        <%--
            <%!%>称作声明
            <%=%>称作jsp表达式，脚本片段
        --%>
        <p>jsp主要是用来回传数据的，并渲染到页面上。jsp本质是servlet</p>
        <p>jsp中通常用
            <%
                String s = "声明java代码脚本";
                System.out.println(s);
            %>
            <%= "声明java代码脚本" %>
        </p>
        <p><%= request.getParameter("username")%></p>
    </div>
</body>
</html>
