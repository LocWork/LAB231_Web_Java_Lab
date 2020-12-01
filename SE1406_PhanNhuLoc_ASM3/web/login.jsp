<%-- 
    Document   : login
    Created on : Oct 21, 2020, 7:28:19 AM
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
                height: 40pt;
                width: 100%;
                padding-left: 12pt;
                background-color: #15a4eb;
                display: flex;
                align-items: center;
                box-shadow: 0pt 0pt 30pt rgba(0,0,0,0.2);
            }
            .menu a {
                margin-right: 12pt;
                font-size: 13pt;
                text-decoration: none;
            }
            .menu a:link, .menu a:visited {
                color: #ffffff;
            }

            .loginBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: none;
                max-width: 40%;
                text-align: center;

            }

            .extraLink{
                text-align: center;
            }

            .extraLink a:link, .extraLink a:visited {
                text-decoration: none;
                color: #4CAF50;
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
            .btn .sub{
                text-decoration: none;
                color: #ffffff;
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
            }
        </style>
        <title>Login Page</title>
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home</a>
        </div>

        <div class="loginContainer">
            <div class="loginBox">
                <h1>Login</h1>
                <div class="loginForm">                    
                    <form action="Login" method="POST">
                        Email: <input type="email" name="txtUserID"/><br/>
                        <font color="red">${requestScope.ERROR.userID}</font><br/>
                        Password: <input type="password" name="txtPassword"/><br/>
                        <font color="red">${requestScope.ERROR.password}</font><br/>
                        <div class="g-recaptcha"
                             data-sitekey="6LdLqdwZAAAAAA49AUIzzsvJWoqXGuVB6wf4VIIo">                                
                        </div>
                        <div class="btn">
                            <input type="submit" value="Enter" class="sub"/><br/>
                            <font color="red">${requestScope.NOTE}</font><br/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="extraLink">
            <a href="register.jsp">Register new account</a><br/>
            <a href="resetInfo.jsp">Forgot your password?</a><br/>
        </div>
    </body>
</html>
