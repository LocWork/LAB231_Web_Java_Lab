<%-- 
    Document   : validate
    Created on : Oct 29, 2020, 8:19:18 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validate Code Page</title>
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

            .resetBox{
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

            .resetForm{
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
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.USER_EMAIL == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.RESET_CODE == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <div class="menu">
            <a href="home.jsp">Home Page</a>
        </div>
        <div class="resetContainer">
            <div class="resetBox">
                <h1>Code input</h1>
                <p>Please check your email for a code</p>
                <div class="resetForm">                    
                    <form action="Validate" method="POST">
                        Code: <input type="text" name="txtCode"/><br/>                      
                        <div class="btn">
                            <input type="submit" value="Send" class="sub"/><br/>
                            <font color="red">${requestScope.NOTE}</font><br/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
