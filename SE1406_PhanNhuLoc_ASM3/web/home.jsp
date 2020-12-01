<%-- 
    Document   : home
    Created on : Oct 19, 2020, 11:04:12 AM
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

            .resultContainer{
                float: left;
                max-width: 60%;
                margin-left: 4%;

            }
            .resultBox{
                margin: 0 auto;

                width: 850px;
            }

            .resultCard{
                background-color: #ffffff;
                width: 100%;
                height: 300px;
                padding: 3%;
                border-radius: 25px;
                border: 2px solid #15a4eb;
            }

            .resultImageContainer{
                max-height: 300px;
                max-width: 300px;
                float: left;
                margin-right: 4%;
            }

            .resultImage{
                height: 300px;
                width: 300px;
                height: 300px;                
            }
            .btnInfo{
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
                color: #ffffff;
                margin-bottom: 4%;
            }

        </style>
        <script>
            function setMinDate() {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1;
                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd
                }
                if (mm < 10) {
                    mm = '0' + mm
                }

                today = yyyy + '-' + mm + '-' + dd;
                document.getElementById("inDate").setAttribute("min", today);
                document.getElementById("outDate").setAttribute("min", today);
            }
        </script>
        <title>Home Page</title>
    </head>
    <body onload="setMinDate()">
        <c:if test="${requestScope.LIST_AREA == null}">
            <c:redirect url="GetList"/>
        </c:if>

        <div class="menu">
            <c:if test="${sessionScope.USER_INFO == null}">
                <a href="login.jsp">Login</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO == null}">
                <a href="register.jsp">Register</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <a href="Logout">Logout</a>
            </c:if>

            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID == 0}">
                    <a href="coupon.jsp">Coupon</a>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID == 1}">
                    <a href="LoadHistory">History</a>
                </c:if>
            </c:if>  

            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID == 1}">
                    <c:if test="${sessionScope.CART != null}">
                        <a href="viewCart.jsp">Cart</a>
                    </c:if>
                </c:if>
            </c:if>  
        </div>


        <div class="about">
            <div class="inner-about">
                <h1>Booking Hotel</h1><br/>
                <p> ${sessionScope.USER_INFO.name}</p><br/>
            </div>
        </div>

        <div class="searchContainer">
            <div class="searchBox">
                <div class="searchForm">
                    <form action="Search" method="POST">
                        Name: <input type="text" name="txtName"/><br/>
                        Area: 
                        <c:if test="${not empty requestScope.LIST_AREA}">
                            <select name="txtArea">
                                <c:forEach var="area" items="${requestScope.LIST_AREA}">
                                    <option value="${area.areaID}">${area.name}</option>
                                </c:forEach>
                            </select>
                        </c:if><br/>
                        Check in date:  <input type="date" name="txtCheckInDate" id="inDate"/><br/>
                        <font color="red">${requestScope.ERROR_SEARCH.checkInDate}</font><br/>
                        Check out date: <input type="date" name="txtCheckOutDate" id="outDate"/><br/>
                        <font color="red">${requestScope.ERROR_SEARCH.checkOutDate}</font><br/>
                        Room: <input type="number" name="txtAmount" min="1" value="1"/><br/>
                        <font color="red">${requestScope.ERROR_SEARCH.amount}</font>
                        <div class="btnSearch">
                            <input type="submit" value="Search" class="subSearch"/><br/>
                        </div>
                        <font color="red">${requestScope.NOTE_SEARCH}</font><br/>
                    </form>
                </div>
            </div>
        </div>

        <div class="resultContainer">                       
            <div class="resultBox">      
                <c:if test="${requestScope.RESULT_LIST != null}">
                    <c:forEach var="info" items="${requestScope.RESULT_LIST}">
                        <div class="resultCard">

                            <div class="resultImageContainer">
                                <a href="HotelRoom?txtHotelID=${info.hotelID}"><image src="images/${info.hotelImage}" class="resultImage"/></a>
                            </div>

                            <div class="resultInfo">
                                <a href="HotelRoom?txtHotelID=${info.hotelID}"><h1>${info.hotelName}</h1></a>
                                <h2>Area: ${info.areaName}</h2>                                                       
                                <p><em>Description: ${info.hotelDescription}</em></p>
                            </div>

                            <div class="resultForm">
                                <form action="HotelRoom" method="POST">
                                    <input type ="hidden" value="${info.hotelID}" name="txtHotelID"/>
                                    <input type="submit" value="Info" class="btnInfo"/>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>

    </body>
</html>
