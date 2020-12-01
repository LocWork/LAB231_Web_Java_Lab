<%-- 
    Document   : orderDetail
    Created on : Oct 16, 2020, 10:34:01 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail Page</title>
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

            .searchBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: 2px solid #000000;
                max-width: 40%;
                text-align: center;

            }
            .searchForm{
                margin: 0 auto;
                text-align: left;
                max-width: 70%;
            }
            .btn{
                margin-top: 4%;
                text-align: center;
                padding-bottom: 3%;
            }


            .detailTable {
                font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
                margin-top: 4%;
                margin-bottom: 2%;
            }

            .detailTable td, .orderTable th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            .detailTable tr:nth-child(even){background-color: #f2f2f2;}

            .detailTable tr:hover {background-color: #ddd;}

            .detailTable th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }

        </style>
    </head>
    <body>     
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:if test="${sessionScope.USER_INFO.roleID == 2}">
                <c:redirect url="home.jsp"/>
            </c:if>
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

        <c:if test="${requestScope.LIST != null}">
            <c:if test="${not empty requestScope.LIST}">
                <table border="1" class="detailTable">
                    <thead>
                        <tr>
                            <th>Detail ID</th>
                            <th>OrderID</th>
                            <th>ProductID</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>CurrentStage</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${requestScope.LIST}">
                            <tr>
                                <td>${dto.detailID}</td>
                                <td>${dto.orderID}</td>
                                <td>${dto.productID}</td>
                                <td>${dto.price}</td>
                                <td>${dto.quantity}</td>
                                <td>${dto.currentStage}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
        </c:if>
    </body>
</html>
