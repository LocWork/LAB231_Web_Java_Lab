<%-- 
    Document   : register
    Created on : Oct 21, 2020, 8:23:23 AM
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

            .registerBox{
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

            .registerForm{
                margin: 0 auto;
                text-align: left;
                max-width: 60%;
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
        <title>Register Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        
        <div class="menu">
            <a href="home.jsp">Home Page</a>
        </div>
        
        <div class="registerContainer">           
            <div class="registerBox">
                <h1>Register</h1>
                <div class="registerForm"> 
                    <form action="Register" method="POST" >
                        Email: <input type="email" name="txtUserID" value="${requestScope.ACCOUNT.userID}"/><br/>
                        <font color="red">${requestScope.ERROR.userID}</font><br/>
                        Name: <input type="text" name="txtName" value="${requestScope.ACCOUNT.name}"/><br/>
                        <font color="red">${requestScope.ERROR.name}</font><br/>
                        Password: <input type="password" name="txtPassword"/><br/>
                        <font color="red">${requestScope.ERROR.password}</font><br/>
                        RePassword: <input type="password" name="txtRePassword"/><br/>
                        <font color="red">${requestScope.ERROR.rePassword}</font><br/>    
                        Phone: <input type="text" name="txtPhone" value="${requestScope.ACCOUNT.phone}"/><br/>
                        <font color="red">${requestScope.ERROR.phone}</font><br/> 
                        Address: <input type="text" name="txtAddress" value="${requestScope.ACCOUNT.address}"/><br/>
                        <font color="red">${requestScope.ERROR.address}</font><br/> 
                        <div class="btn">
                            <input type="submit" value="Register" class="sub"/><br/>  
                            <font color="red">${requestScope.NOTE}</font><br/> 
                        </div>

                    </form>
                </div>
            </div>
        </div>
        <div class="extraLink">
            <a href="login.jsp">Login page</a>  
        </div>

    </body>
</html>
