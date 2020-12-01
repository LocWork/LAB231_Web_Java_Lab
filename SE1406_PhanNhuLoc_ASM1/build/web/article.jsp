<%-- 
    Document   : article
    Created on : Sep 21, 2020, 11:12:41 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Article Page</title>
        <style>
            body{
                background-color: #bbfc88;
            }
            .hub{
                text-align: center;
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
            .emotion{
                text-align: center;
                margin: 0 auto;
                background-color: #ffffff;
            }
            .articleComment{
                text-align: center;
            }

        </style>
    </head>
    <body>
        <!-- Redirct -->
        <c:if test="${sessionScope.USER_INFO == null || sessionScope.USER_INFO.status == 0}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <div class ="hub">
            <a href="SearchController?txtSearch=${requestScope.SEARCH}&action=Search">Return to home page</a>
            <c:if test="${sessionScope.USER_INFO.email eq requestScope.ARTICLE.email}">
                <a href="MainController?action=DeleteArticle&txtPostID=${requestScope.ARTICLE.postID}&txtEmail=${requestScope.ARTICLE.email}" onclick="return confirm('Are you sure ?')">DeleteArticle</a>
            </c:if>   
        </div>
        <div class="fullArticlePage">
            <table border="1" class="article">
                <thead>
                    <tr>
                        <th>${requestScope.ARTICLE.title}</a</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Article section -->
                    <tr>
                        <td class="articleContent">Author: ${requestScope.ARTICLE.email}</td>
                    </tr>
                    <tr>
                        <td class="articleContent">Date: ${requestScope.ARTICLE.date}</td>
                    </tr>
                    <tr>
                        <td class="articleContent">Description: ${requestScope.ARTICLE.description}</td>
                    </tr>

                    <tr>
                        <td><image src="${requestScope.ARTICLE.image}" class="articleImage"/></td>
                    </tr>
                    <!-- Article Emotion -->
                    <tr>
                        <td>
                            <div class="emotion">    
                                <a href="MainController?action=ChooseEmotion&type=Like&txtId=${requestScope.ARTICLE.postID}&txtSearch=${requestScope.SEARCH}"><image src="D:\JavaWeb\SE1406_PhanNhuLoc_ASM1\pic\like.png" width="50" height="50"/></a>
                                    ${requestScope.EMOTION_LIKE}
                                <a href="MainController?action=ChooseEmotion&type=Dislike&txtId=${requestScope.ARTICLE.postID}&txtSearch=${requestScope.SEARCH}"><image src="D:\JavaWeb\SE1406_PhanNhuLoc_ASM1\pic\dislike.png" width="50" height="50"/></a>
                                    ${requestScope.EMOTION_DISLIKE}
                            </div>
                        </td>

                    </tr>

                    <tr>
                        <td>
                            <div class="articleComment">
                                <form action="MainController" method="POST">
                                    Comment:
                                    <input type="text" name="txtComment"/>
                                    <input type="hidden" name="txtId" value="${requestScope.ARTICLE.postID}"/>
                                    <input type="hidden" name="txtEmail" value="${sessionScope.USER_INFO.email}"/>
                                    <input type="hidden" name="txtSearch" value="${requestScope.SEARCH}"/>
                                    <input type="submit" name="action" value="CommentArticle"/>
                                </form>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>

                            <c:if test="${requestScope.COMMENT_LIST != null}">
                                <c:if test="${not empty requestScope.COMMENT_LIST}">

                                    <c:forEach var="com" items="${requestScope.COMMENT_LIST}">

                                <tr>
                                    <td>${com.email}: ${com.comment}

                                        <c:if test="${com.email eq sessionScope.USER_INFO.email}">

                                            <a href="MainController?action=DeleteComment&txtCommentID=${com.id}&txtSearch=${requestScope.SEARCH}&txtId=${requestScope.ARTICLE.postID}" onclick="return confirm('Are you sure ?')">remove</a></td>
                                        </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </c:if>
            </c:if>    

        </td>         
    </tr>
</tbody>

</table>
</div>                     
</body>
</html>
