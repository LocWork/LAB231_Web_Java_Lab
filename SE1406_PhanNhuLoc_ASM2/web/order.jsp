<%-- 
    Document   : order
    Created on : Oct 16, 2020, 8:20:14 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Page</title>
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


            .orderTable {
                font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
                margin-top: 4%;
                margin-bottom: 2%;
            }

            .orderTable td, .orderTable th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            .orderTable tr:nth-child(even){background-color: #f2f2f2;}

            .orderTable tr:hover {background-color: #ddd;}

            .orderTable th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }
            .text{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>


        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
        </div>

        <c:if test="${sessionScope.USER_INFO.roleID == 1 || sessionScope.USER_INFO.roleID == 0}">
            <div class="searchContainer">
                <div class="searchBox">
                    <h1>Search Order</h1>
                    <div class="searchForm">
                        <form action="Order">
                            Search(OrderID): <input type="text" name="txtOrderID" value="${requestScope.SEARCH}"/>
                            <input type="hidden" name="txtUserID" value="${sessionScope.USER_INFO.userID}">
                            <input type="hidden" name="txtRoleID" value="${sessionScope.USER_INFO.roleID}"/>
                            <div class="btn">
                                <input type="submit" value="Search"/><br/>
                                <font color="red">${requestScope.NOTE}</font><br/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>


        <c:if test="${ORDER_LIST != null}">
            <c:if test="${not empty ORDER_LIST}">
                <table border="1" class="orderTable">
                    <thead>
                        <tr>
                            <th>OrderID</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Total</th>
                            <th>Date</th>
                            <th>Payment</th>
                            <th>More</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${ORDER_LIST}">
                            <tr>
                                <td>${dto.orderID}</td>
                                <td>${dto.name}</td>
                                <td>${dto.address}</td>
                                <td>${dto.total}</td>
                                <td>${dto.date}</td>
                                <td>${dto.payment}</td>

                                <td>
                                    <form action="OrderDetail" method="POST">
                                        <input type="hidden" name="txtOrderID" value="${dto.orderID}"/>
                                        <input type="submit" value="More"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
        </c:if>




        <c:if test="${requestScope.ORDER_ID_LIST != null}">
            <c:if test="${not empty requestScope.ORDER_ID_LIST}">

                <p class="text">Your recent OrderID</p>
                <table border="1" class="orderTable">
                    <thead>
                        <tr>
                            <th>OrderID</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${requestScope.ORDER_ID_LIST}">
                            <tr>
                                <td>${dto}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>    
    </body>
</html>
