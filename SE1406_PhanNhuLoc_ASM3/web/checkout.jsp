<%-- 
    Document   : checkout
    Created on : Oct 31, 2020, 4:30:02 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Out Page</title>
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
            .about {
                height: calc(100% - 60pt);
                margin: 2%;                
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .inner-about {
                width: 100%;
                max-width: 600pt;
                background-color: #ffffff;
                padding: 10pt;
                border-radius: 25px;
                text-align: center;
            }
            .inner-about h1 {
                margin: 0;
                font-size: 20pt;
                margin-bottom: 2%;
                color: #000000;

            }
            .inner-about p {
                margin: 0;
                margin-bottom: 8pt;
                font-size: 15pt;
                color: #000000;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:if test="${sessionScope.USER_INFO.roleID == 0}">
                <c:redirect url="home.jsp"/>
            </c:if>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home</a>
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                    <a href="LoadHistory">History</a>
                </c:if>
            </c:if>
        </div>

        <div class="about">
            <div class="inner-about">
                <h1>Booking Completed</h1><br/>
                <p>SUCCESS</p>
            </div>
        </div>
    </body>
</html>
