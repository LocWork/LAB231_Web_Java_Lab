<%-- 
    Document   : room
    Created on : Oct 27, 2020, 8:44:41 PM
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

            .roomContainer{
                float: left;
                max-width: 60%;
                margin-left: 4%;

            }
            .roomBox{
                margin: 0 auto;

                width: 850px;
            }

            .roomCard{
                background-color: #ffffff;
                width: 100%;
                height: 300px;
                padding: 3%;
                border-radius: 25px;
                border: 2px solid #15a4eb;
            }

            .roomImageContainer{
                max-height: 300px;
                max-width: 300px;
                float: left;
                margin-right: 4%;
            }

            .roomImage{
                height: 300px;
                width: 300px;
                height: 300px;                
            }
            .btnBook{
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
                color: #ffffff;
                margin-bottom: 4%;
            }
        </style>
        <title>Room Page</title>
    </head>
    <body>

        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <c:if test="${sessionScope.USER_INFO == null}">
                <a href="login.jsp">Login</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO == null}">
                <a href="register.jsp">Register</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <a href="Logout">Logout</a>
            </c:if>
            <c:if test="${sessionScope.CART != null}">
                <a href="viewCart.jsp">Cart</a>
            </c:if>
        </div>


        <div class="roomContainer">                       
            <div class="roomBox">      
                <c:if test="${requestScope.ROOM_LIST != null}">
                    <c:forEach var="info" items="${requestScope.ROOM_LIST}">
                        <div class="roomCard">

                            <div class="roomImageContainer">
                                <image src="images/${info.image}" class="roomImage"/></a>
                            </div>

                            <div class="roomInfo">
                                <p>Type: ${info.typeID}</p>
                                <h2>Price: ${info.price} $</h2>                                                       
                                <p>Amount: ${info.amount}</p>
                                <p><em>${info.description}</em></p>
                            </div>

                            <c:if test="${sessionScope.USER_INFO != null}">
                                <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                                    <div class="roomForm">
                                        <form action="AddCart" method="POST">
                                            <input type="hidden" name="txtHotelID" value="${info.hotelID}"/>
                                            <input type="hidden" name="txtRoomID" value="${info.roomID}"/>
                                            <input type="submit" value="Book Room" class="btnBook"/>
                                        </form>
                                    </div>
                                </c:if>
                            </c:if>

                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </body>
</html>
