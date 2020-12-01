<%-- 
    Document   : register
    Created on : Sep 16, 2020, 11:25:34 AM
    Author     : hi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <style>
            body{
                background-color: #bbfc88;
            }
            .registerForm{
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
        <div>
            <form action="MainController" method="POST" class="registerForm">
                <p>Register</p>
                Email: <input type="email" name="txtEmail" value="${requestScope.ACCOUNT.email}"/><br/>
                <font color="red">${requestScope.ERROR.email}</font><br/>
                Name: <input type="text" name="txtName" value="${requestScope.ACCOUNT.name}"/><br/>
                <font color="red">${requestScope.ERROR.name}</font><br/>
                Password: <input type="password" name="txtPassword"/><br/>
                <font color="red">${requestScope.ERROR.password}</font><br/>
                RePassword: <input type="password" name="txtRePassword"/><br/>
                <font color="red">${requestScope.ERROR.rePassword}</font><br/>          
                <input type="submit" value="Register" name="action"/><br/>
                <a href="login.jsp">Return to the login page</a>
            </form>

        </div>
    </body>
</html>
