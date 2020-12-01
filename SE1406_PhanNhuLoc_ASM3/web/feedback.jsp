<%-- 
    Document   : rate
    Created on : Nov 1, 2020, 8:26:22 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FeedBack Page</title>
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

           
            .rateBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: none;
                max-width: 40%;
                text-align: center;
            }

            .rateSearch{
                text-align: center;
                padding-bottom: 3%;

            }
            .rateSearch .subSearch{
                margin: 2%;
                text-decoration: none;
                color: #ffffff;
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
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
        
        <c:if test="${requestScope.ROOM_ID == null}">
            <c:redirect url="LoadHistory"/>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home</a>
        </div>

        <div class="rateContainer">
            <div class="rateBox">
                <div class="rateForm">
                    <h1>FeedBack</h1>
                    <form action="FeedBack" method="POST">
                        <input type="hidden" name="txtRoomID" value="${requestScope.ROOM_ID}"/><br/>
                        Score: <input type="number" name="txtScore" min="0" max="10" value="0"/><br/>
                        Description: <input type="text" name="txtDescription"/><br/>
                        <div class="rateSearch">
                            <input type="submit" value="Rate" class="subSearch"/><br/>
                        </div>
                        <font color="red">${requestScope.NOTE}</font><br/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
