<%-- 
    Document   : bookSearch
    Created on : Nov 1, 2020, 1:14:14 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Search Page</title>
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

            .searchContainer{
                margin-bottom: 3%;
                float: left;
                max-height: 100%;
                max-width: 40%;
            }
            .searchBox{
                background-color: #ffffff;
                width: 100%;
                max-width: 200pt;
                padding: 10pt;
                border-radius: 25px;
                border: 2px solid #15a4eb;
                margin: 0 auto;
            }

            .btnSearch{
                text-align: center;
                padding-bottom: 3%;

            }
            .btnSearch .subSearch{
                margin: 2%;
                text-decoration: none;
                color: #ffffff;
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
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

            .searchContainer{
                margin-bottom: 3%;
                float: left;
                max-height: 100%;
                max-width: 40%;
            }
            .searchBox{
                background-color: #ffffff;
                width: 100%;
                max-width: 200pt;
                padding: 10pt;
                border-radius: 25px;
                border: 2px solid #15a4eb;
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <c:if test="${sessionScope.USER_INFO != null}">
            <c:if test="${sessionScope.USER_INFO.roleID != 1}">
                <c:redirect url="home.jsp"/>
            </c:if>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <c:if test="${sessionScope.USER_INFO != null}">
                <a href="Logout">Logout</a>
            </c:if>
        </div>

        <div class="searchContainer">
            <div class="searchBox">
                <div class="searchForm">
                    <form action="SearchHistory" method="POST">
                        ID: <input type="text" name="txtBookingID"/><br/>
                        Date:<input type="date" name="txtBookingDate"/><br/>
                        <div class="btnSearch">
                            <input type="submit" value="Search" class="subSearch"/>
                        </div>
                        <font color="red">${requestScope.NOTE}</font>
                    </form>
                </div>
            </div>
        </div>

        <c:if test="${requestScope.LIST != null}">
            <c:if test="${not empty requestScope.LIST}">
                <table border="1" class="orderTable">
                    <thead>
                        <tr>
                            <th>BookID</th>
                            <th>UserID</th>
                            <th>BookingDate</th>
                            <th>Total</th>
                            <th>DiscountCode</th>
                            <th>Detail</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${requestScope.LIST}">
                            <tr>
                                <td>${dto.bookingID}</td>
                                <td>${dto.userID}</td>
                                <td>${dto.bookingDate}</td>
                                <td>${dto.total}</td>
                                <td>${dto.discountID}</td>
                                <td>
                                    <form action="HistoryDetail" method="POST">
                                        <input type="hidden" name="txtBookingID" value="${dto.bookingID}"/>
                                        <input type="submit" value="More"/>
                                    </form>
                                </td>
                                <td>
                                    <form action="DeleteHistory" method="POST">
                                        <input type="hidden" name="txtBookingID" value="${dto.bookingID}"/>
                                        <input type="submit" value="Delete" onclick="return confirm('Are you sure ?')"/>
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
