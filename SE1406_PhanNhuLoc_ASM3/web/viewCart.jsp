<%-- 
    Document   : viewCart
    Created on : Oct 28, 2020, 10:45:14 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
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

            .roomTable {
                font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
                margin-top: 4%;
                margin-bottom: 2%;
            }

            .roomTable td, .orderTable th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            .roomTable tr:nth-child(even){background-color: #f2f2f2;}

            .roomTable tr:hover {background-color: #ddd;}

            .roomTable th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }
            .roomImage{
                padding-top: 3%;
                max-width: 100px;
                max-height: 100px;
            }
        </style>
    </head>
    <body>

        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <c:if test="${sessionScope.USER_INFO.roleID == 0}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home Page</a> 
        </div>

        <c:if test="${sessionScope.CART != null}">
            <c:if test="${not empty sessionScope.CART}">
                <table border="1" class="roomTable">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Image</th>
                            <th>HotelName</th>                                     
                            <th>Room Type</th>
                            <th>CheckInDate</th>
                            <th>CheckOutDate</th>
                            <th>Price</th>
                            <th>Amount</th> 
                            <th>Delete</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${sessionScope.CART.getCart().values()}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><image src="images/${dto.image}" class="roomImage"/></td>
                                <td>${dto.hotelName}</td>                 
                                <td>${dto.typeID}</td>
                                <td>${dto.checkInDate}</td>
                                <td>${dto.checkOutDate}</td>
                                <td>${dto.price}</td>
                                <td>
                                    <form action="UpdateCart" method="POST">
                                        <input type="hidden" name="txtRoomID" value="${dto.roomID}"/>
                                        <input type="hidden" name="txtCheckInDate" value="${dto.checkInDate}"/>
                                        <input type="hidden" name="txtCheckOutDate" value="${dto.checkOutDate}"/>
                                        <input type="hidden" name="txtHotelID" value="${dto.hotelID}"/>
                                        <input type="number" name="txtAmount" value="${dto.amount}" min="1"/>
                                        <input type="submit" value="Update" onclick="return confirm('Are you sure ?')"/>
                                    </form>
                                </td>
                                <td>
                                    <form action="DeleteCart" method="POST">
                                        <input type="hidden" name="txtRoomID" value="${dto.roomID}">
                                        <input type="submit" value="Delete" onclick="return confirm('Are you sure ?')"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <font color="red">${requestScope.NOTE}</font><br/>
                Total:<font color="red">${sessionScope.TOTAL}</font><br/>
                <c:if test="${sessionScope.DISCOUNT_CODE != null}">
                    ${sessionScope.DISCOUNT_CODE}
                    <font color="red">Your discount has been applies</font>
                </c:if>
            </c:if>
        </c:if>

        <c:if test="${sessionScope.TOTAL > 0}">
            <c:if test="${sessionScope.DISCOUNT_CODE == null}">
                <form action="Coupon" method="POST">
                    <input type="text" name="txtCode"/>
                    <input type="submit" value="Submit"/><br/>
                </form>
            </c:if>
        </c:if>
        
            <c:if test="${sessionScope.DISCOUNT_CODE != null}">
                <form action="ChangeCoupon" method="POST">
                    <input type="submit" value="Change"/><br/>
                </form>
            </c:if>
     
        <font color="red">${requestScope.COUPON_ERROR}</font>

        <c:if test="${not empty sessionScope.CART}">
            <form action="CheckoutMail">
                <input type="submit" value="Checkout" onclick="return confirm('Are you sure ?')"/>
            </form>
        </c:if>   
        
    </body>
</html>
