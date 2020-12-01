<%-- 
    Document   : bookDetail
    Created on : Nov 1, 2020, 1:12:31 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Detail Page</title>
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
        
        <c:if test="${sessionScope.USER_INFO.roleID == 0}">
            <c:redirect url="home.jsp"/>
        </c:if>


        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <c:if test="${sessionScope.USER_INFO != null}">
                <a href="Logout">Logout</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                    <a href="LoadHistory">History</a>
            </c:if> 
        </div>


        <c:if test="${requestScope.DETAIL_LIST != null}">
            <c:if test="${not empty requestScope.DETAIL_LIST}">
                <table border="1" class="orderTable">
                    <thead>
                        <tr>
                            <th>DetailID</th>
                            <th>RoomID</th>
                            <th>HotelID</th>
                            <th>Price</th>
                            <th>Amount</th>
                            <th>CheckInDate</th>
                            <th>CheckOutDate</th>
                            <th>Rate</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${requestScope.DETAIL_LIST}">
                            <tr>
                                <td>${dto.detailID}</td>
                                <td>${dto.roomID}</td>
                                <td>${dto.hotelID}</td>
                                <td>${dto.price}</td>
                                <td>${dto.amount}</td>
                                <td>${dto.checkInDate}</td>
                                <td>${dto.checkOutDate}</td>
                                <td>
                                    <form action="FeedBackInfo" method="POST">
                                        <input type="hidden" value="${dto.roomID}" name="txtRoomID"/>
                                        <input type="hidden" value="${dto.checkOutDate}" name="txtCheckOutDate"/>
                                        <input type ="submit" value="Rate"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>
    </body>
</html>
