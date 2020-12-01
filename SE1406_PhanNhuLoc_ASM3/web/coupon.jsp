<%-- 
    Document   : coupon
    Created on : Oct 29, 2020, 9:09:02 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Coupon Page</title>
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

            .couponBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: none;
                max-width: 40%;
                text-align: center;

            }

            .extraLink{
                text-align: center;
            }

            .extraLink a:link, .extraLink a:visited {
                text-decoration: none;
                color: #4CAF50;
                text-align: center;
            }

            .couponForm{
                margin: 0 auto;
                text-align: left;
                max-width: 60%;
            }

            .btn{
                text-align: center;
                padding-bottom: 3%;

            }
            .btn .sub{
                text-decoration: none;
                color: #ffffff;
                background-color: #4CAF50;
                padding: 10px 20px;
                border: none;
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
                document.getElementById("exDate").setAttribute("min", today);
            }
        </script>
    </head>

    <body onload="setMinDate()">

        <c:if test="${sessionScope.USER_INFO.roleID != 0}">
            <c:redirect url="home.jsp"/>
        </c:if>

        <div class="menu">
            <a href="home.jsp">Home Page</a>
        </div>
        <div class="couponContainer">           
            <div class="couponBox">
                <h1>Create Coupon</h1>
                <div class="couponForm"> 
                    <form action="CreateCoupon" method="POST" >
                        Code: <input type="text" name="txtCode" value="${requestScope.COUPON.code}"/><br/>
                        <font color="red">${requestScope.ERROR.codeID}</font><br/>
                        Name: <input type="text" name="txtName" value="${requestScope.COUPON.name}"/><br/>
                        <font color="red">${requestScope.ERROR.codeName}</font><br/>
                        Amount: <input type="number" name="txtAmount" min="1" max="100" value="1"/><br/>
                        <font color="red">${requestScope.ERROR.amount}</font><br/> 
                        ExDate: <input type="date" name="txtExDate" id="exDate"/><br/>
                        <font color="red">${requestScope.ERROR.expireDate}</font><br/>
                        <div class="btn">
                            <input type="submit" value="Create" class="sub"/><br/>  
                            <font color="red">${requestScope.NOTE}</font><br/> 
                        </div>
                    </form>
                </div>
            </div>
        </div>


    </body>
</html>
