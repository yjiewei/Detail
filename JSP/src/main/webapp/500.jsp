<%@ page import="com.yjiewei.JSP.Student" %><%--
  Created by IntelliJ IDEA.
  User: yjiewei
  Date: 2021/11/6
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
    isErrorPage="true"
%>
<html>
<head>
    <title>500错误页面</title>
</head>
<body>
    <%
        Student stu = new Student();
        stu.setName("yjiewei");
        stu.setAge(23);
        request.setAttribute("myself", stu);
    %>
    ${myself}
</body>
</html>
