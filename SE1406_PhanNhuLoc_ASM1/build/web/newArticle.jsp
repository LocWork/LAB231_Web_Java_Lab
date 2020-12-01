<%-- 
    Document   : newArticle
    Created on : Sep 24, 2020, 10:11:06 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Article Page</title>
        <style>
            body{
                background-color: #bbfc88;
            }
            .articleForm{
                margin: 0 auto;
                background-color: #ffffff;
                align-content: center;
                width: 30%;
                height: 50%;
                border-radius: 10px;
                text-align: center;
            }

        </style> 
    </head>
    <body>
        <!-- Redirct -->
        <c:if test="${sessionScope.USER_INFO == null || sessionScope.USER_INFO.status == 0}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <!-- Article Form -->
        <form action="MainController" method="POST" class="articleForm">
            Title <input type="text" name="txtTitle"/><br/>
            <font color ="red">${requestScope.ERROR_ARTICLE.title}</font><br/>
            Description<input type="text" name="txtDescription"/><br/>
            <font color ="red">${requestScope.ERROR_ARTICLE.description}</font><br/>
            Image<input type="text" name="txtImage"/><br/>
            <font color ="red">${requestScope.ERROR_ARTICLE.image}</font><br/>
            <input type="submit" name="action" value="CreateArticle"/><br/>
            <a href="home.jsp">Return to home page</a>
        </form>

    </body>
</html>
