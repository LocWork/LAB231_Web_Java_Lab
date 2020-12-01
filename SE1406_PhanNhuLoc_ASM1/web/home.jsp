<%-- 
    Document   : home
    Created on : Sep 16, 2020, 11:16:27 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <style>
            h1{
                background-color: #bbfc88;
                text-align: center;
            }
            body{
                background-color: #bbfc88;
            }
            .hub{
                margin: 0 auto;
                background-color: #ffffff;
                align-content: center;
                width: 30%;
                height: 50%;
                border-radius: 10px;
                text-align: center;
            }
            .searchForm{
                margin-top: 20px;
                padding-bottom: 20px;
                background-color: #ffffff;
            }

            .articleContainer{

            }

            .article{
                margin: 0 auto;
                width: 100px;
                height: 200px;
                margin-top: 2%;
                margin-bottom: 2%;
                border-collapse: collapse;
                border: 1px solid black;
                background-color: #ffffff;
            }
            .articleImage{

                width: 400px;
                height: 300px;
            }
            .articleContent{
                text-align: left;
                padding-left: 2%;
            }
            .articleNavication{
                margin: 0 auto;
                text-align: center;
            }

        </style>
    </head>
    <body>
        <!-- Redirct -->
        <c:if test="${sessionScope.USER_INFO == null || sessionScope.USER_INFO.status == 0}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <!-- Hub -->
        <h1>Welcome ${sessionScope.USER_INFO.name}</h1>
        <div class="hub">
            <a href="newArticle.jsp">Create new article</a>
            <a href="MainController?action=Notify">View notify</a>
            <a href="MainController?action=Logout">Logout</a><br/>
            <form action="MainController" method="POST" class="searchForm">
                Search <input type="text" name="txtSearch" value="${requestScope.SEARCH}"/>
                <input type="hidden" name="txtPageIndex" value="0">      
                <input type="submit" value="Search" name="action"/>
            </form>
        </div>

        <!-- Search -->
        <div class="articleContainer">
            <c:if test="${requestScope.LIST != null}">
                <c:if test="${not empty requestScope.LIST}">
                    <c:forEach var="dto" items="${requestScope.LIST}">
                        <table class="article">
                            <thead>
                                <tr>
                                    <th><a href="MainController?action=ViewArticle&txtSearch=${requestScope.SEARCH}&txtId=${dto.postID}&txtPageIndex=${sessionScope.PAGE_INDEX}">${dto.title}</a></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="articleContent">Author: ${dto.email}</td>
                                </tr>
                                <tr>
                                    <td class="articleContent">Date: ${dto.date}</td>
                                </tr>
                                <tr>
                                    <td class="articleContent">Description: ${dto.description}</td>
                                </tr>

                                <tr>
                                    <td><image src="${dto.image}" class="articleImage"/></td>
                                </tr>

                            </tbody>
                        </table>
                    </c:forEach>
                </c:if>
            </c:if>

            <div class="articleNavication">

                <c:if test="${requestScope.PAGE_INDEX != null && requestScope.PAGE_COUNT gt 1}">

                    <c:if test="${requestScope.PAGE_INDEX gt 1}">
                        <a href="MainController?action=Previous&txtSearch=${requestScope.SEARCH}&txtPageIndex=${requestScope.PAGE_INDEX}&txtPageCount=${requestScope.PAGE_COUNT}">Previous Page</a>
                    </c:if>

                    <c:if test="${requestScope.PAGE_INDEX lt requestScope.PAGE_COUNT}">
                        <a href="MainController?action=Next&txtSearch=${requestScope.SEARCH}&txtPageIndex=${requestScope.PAGE_INDEX}&txtPageCount=${requestScope.PAGE_COUNT}">Next Page</a>
                    </c:if>
                </c:if>

            </div>

        </div>
    </body>
</html>
