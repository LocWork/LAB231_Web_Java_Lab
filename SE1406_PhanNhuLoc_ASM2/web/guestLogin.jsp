<%-- 
    Document   : guestLogin
    Created on : Oct 14, 2020, 11:08:14 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guest Login Page</title>
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

            .loginForm{
                margin: 0 auto;
                text-align: left;
                max-width: 50%;
            }
            .btn{
                text-align: center;
                padding-bottom: 3%;
            }

            .extraLink{
                text-align: center;
            }
        </style>
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
                <h1>Guest Login</h1>
                <div class="loginForm">
                    <form action="GuestLogin" method="POST">
                        Phone:<input type="text" name="txtPhoneNumber"/><br/>
                        <font color="red">${requestScope.PHONE_ERROR}</font><br/>
                        Name:<input type="text" name="txtName"/><br/>
                        <font color="red">${requestScope.NAME_ERROR}</font><br/>
                        Address:<input type="text" name="txtAddress"/><br/>
                        <font color="red">${requestScope.ADDRESS_ERROR}</font> <br/> 
                        <div class="btn">
                            <input type="submit" value="Login"/><br/>
                            <font color="red">${requestScope.NOTE}</font><br/>  
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="extraLink">
            <a href="login.jsp">Login as a user</a><br/>
        </div>


    </body>
</html>
