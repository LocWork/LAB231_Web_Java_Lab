<%-- 
    Document   : login
    Created on : Oct 5, 2020, 7:08:32 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            html {
                margin: auto;
                overflow: scroll;
            }
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                background-color: #f2f2f2;
                height: 100%;
                overflow: hidden;
            }
            .menu {
                height: 60pt;
                width: 100%;
                padding-left: 12pt;
                background-color: #000000;
                display: flex;
                margin-bottom: 3%;
                align-items: center;
                box-shadow: 0pt 0pt 30pt rgba(0,0,0,0.2);
            }
            .menu a {
                margin-right: 12pt;
                font-size: 13pt;
                text-decoration: none;
            }
            .menu a:link, .menu a:visited {
                color: #f7f7c8;
            }
            .loginBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: 2px solid #000000;
                max-width: 40%;
                text-align: center;

            }

            .extraLink{
                text-align: center;
            }

            .loginForm{
                margin: 0 auto;
                text-align: left;
                max-width: 50%;
            }
            .btn{
                text-align: center;
                padding-bottom: 3%;
            }


        </style>
        <title>Login Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
        </div>


        <div class="loginContainer">
            <div class="loginBox">
                <h1>Login</h1>
                <div class="loginForm">
                    <form action="Login" method="POST">
                        UserID:<input type="email" name="txtUserID"/><br/>
                        <font color="red">${requestScope.USER_ERROR.userIDError}</font><br/>
                        Password:<input type="password" name="txtPassword" /><br/>
                        <font color="red">${requestScope.USER_ERROR.passwordError}</font><br/>
                        <div class="btn">
                            <input type="submit" value="Login" name="action"/><br/>
                            <font color="red">${requestScope.NOTE}</font><br/>
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <div class="extraLink">
            <a href="guestLogin.jsp" class="guestLink">Login as a guest</a><br/>
        </div>
    </body>
</html>
