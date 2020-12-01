<%-- 
    Document   : home
    Created on : Oct 7, 2020, 8:18:31 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
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
                position: fixed;
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
                color: #f7f7c8;
            }
            .about {
                background-color: #e6e7ed;
                color: #fff;
                height: calc(100% - 60pt);
                padding-top: 60pt;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .inner-about {
                background-color: #ffffff;
                width: 100%;
                max-width: 600pt;
                padding: 10pt;
                border-radius: 25px;
                border: 2px solid #000000;
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
        <div class="menu">
            <a href="ProductPage">Product</a>
            <c:if test="${sessionScope.USER_INFO == null}">        

                <a href="login.jsp">Login</a>

            </c:if>
            <c:if test="${sessionScope.CART != null}">
                <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                    <a href="viewCart.jsp">Cart</a>
                </c:if>
            </c:if>

            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID != 2}">
                    <a href="LoadOrderID">Order</a>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">

                <a href="Logout">Logout</a>

            </c:if>
            <c:if test="${sessionScope.USER_INFO.roleID == 0}">
                <a href="AdminLoadData">UserData</a>
            </c:if>
                <a href="coupon.jsp">Coupon</a>
        </div>

        <div class="about">
            <div class="inner-about">
                <h1>WECOME TO THE YELLOW MOON SHOP</h1>
                <h1>${sessionScope.USER_INFO.name}</h1>
                <p>This is just a home page</p>
            </div>
        </div>

    </body>
</html>
