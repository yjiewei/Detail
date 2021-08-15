<%--
  Created by IntelliJ IDEA.
  User: yangjiewei
  Date: 2021/8/14
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>ajax的GET方式请求</title>
</head>
<%--ctrl + F5 可以强行刷新页面--%>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script>
    $(function () {
        // 1.绑定用户名文本框失去焦点的时候触发
        $("#username").blur(function () {
            // console.log("我失去焦点啦，你们快点去检验好吧");
            // 发送ajax请求，验证是否存在相同的用户名
            // 1.获取用户输入值
            var username = $(this).val();
            // 2.发送ajax的get请求
            // a.创建xhr对象
            var xhr;
            if (window.XMLHttpRequest) {
                xhr = new XMLHttpRequest();
            }else {
                xhr = new XMLHttpRequest("Microsoft.XMLHTTP");
            }
            // b.发送请求并传递参数 url为请求接口
            xhr.open("GET", "${pageContext.request.contextPath}/user/checkUsername?username="+username);
            xhr.send();

            // c.处理响应并获取返回的字符串
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // 处理正确返回字符串的响应
                    console.log(xhr.responseText); // 响应结果
                    // $("#msg").text(xhr.responseText);
                    $("#msg").html("<font color='red'>" + xhr.responseText + "</font>");
                }
            }


        })
    })
</script>
<body>

    <form action="" enctype="application/x-www-form-urlencoded">
        用户名：<input id="username" type="text" class="text"> <span id="msg"></span> <br>

    </form>
</body>
</html>
