<%-- 
    Document   : login
    Created on : Sep 16, 2020, 11:41:04 AM
    Author     : hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
            body{
                background-color: #bbfc88;
            }
            .loginForm{
                margin: 0 auto;
                background-color: #ffffff;
                align-content: center;
                width: 40%;
                height: 50%;
                border-radius: 10px;
                text-align: center;
            }
            .link{
                text-align: center;
            }
        </style>
    </head>
    <body>       
        <form action="MainController" method="POST" class="loginForm">
            <p>Login</p>
            Email: <input type="email" name="txtEmail" /><br/>
            <font color="red">${requestScope.ERROR_LOGIN.email}</font><br/>
            Password: <input type="password" name="txtPassword" ><br/>
            <font color="red">${requestScope.ERROR_LOGIN.password}</font><br/>
            <input type="submit" value="Login" name="action"/><br/>
            <font color="red">${requestScope.NOTE}</font><br/>
            <a href="register.jsp" class="link">Sign up</a>
        </form>        
    </body>
</html>
