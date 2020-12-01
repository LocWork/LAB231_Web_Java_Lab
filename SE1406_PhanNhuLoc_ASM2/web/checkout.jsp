<%-- 
    Document   : checkout
    Created on : Oct 15, 2020, 9:20:23 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>
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

            .detail{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: 2px solid #000000;
                max-width: 40%;
                text-align: center;

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
        <c:if test="${requestScope.ORDER_ID == null}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID != 2}">
                    <a href="LoadOrderID">Order</a>
                </c:if>
            </c:if>
        </div>

        <div class="detailContainer">
            <div class="detail">
                <h1>Check out page</h1>
                <c:if test="${requestScope.STATE == 1}">
                    <p>Your order is success</p>
                    <c:if test="${sessionScope.USER_INFO.roleID != 2}">
                        <p>Your orderID: ${requestScope.ORDER_ID}</p>
                    </c:if>
                </c:if>
                <c:if test="${requestScope.STATE == 0}">
                    <p>Your order fail</p>
                </c:if>

            </div>
        </div>
    </body>
</html>
