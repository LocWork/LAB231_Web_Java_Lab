<%-- 
    Document   : notify
    Created on : Sep 26, 2020, 3:10:46 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notify Page</title>
    </head>
    <style>
        body{
            background-color: #bbfc88;
        }
        .innerBox{
            width: 60%;
            height: 30%;
            margin: 0 auto;
            background-color: #ffffff;
        }

    </style>
    <body>
        <c:if test="${sessionScope.USER_INFO == null || sessionScope.USER_INFO.status == 0}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <div class="container">
            <div class="innerBox">
                <c:if test="${sessionScope.USER_INFO == null}">
                    <c:redirect url="login.jsp"/>
                </c:if>
                <c:if test="${requestScope.NOTIFY_LIST != null}">
                    <c:if test="${not empty requestScope.NOTIFY_LIST}">
                        <c:forEach var="dto" items="${requestScope.NOTIFY_LIST}" >
                            <a href="MainController?action=ViewArticle&txtId=${dto.postID}&txtSearch=${requestScope.SEARCH}">User: ${dto.email} has ${dto.type} this post: ${dto.id} on ${dto.date} </a><br/>
                        </c:forEach> 
                    </c:if>
                </c:if>
                <a href="home.jsp">Return to home page</a>
            </div>
        </div>
    </body>
</html>
