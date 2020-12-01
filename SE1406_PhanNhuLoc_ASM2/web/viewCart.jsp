<%-- 
    Document   : viewCart
    Created on : Oct 13, 2020, 8:58:24 PM
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

            .productTable {
                font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
                margin-top: 4%;
                margin-bottom: 2%;
            }

            .productTable td, .orderTable th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            .productTable tr:nth-child(even){background-color: #f2f2f2;}

            .productTable tr:hover {background-color: #ddd;}

            .productTable th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }
            .productImage{
                padding-top: 3%;
                max-width: 100px;
                max-height: 100px;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.CART == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="guestLogin.jsp"/>
        </c:if>

        <c:if test="${sessionScope.USER_INFO.roleID == 0}">
            <c:redirect url="home.jsp"/>
        </c:if>
        
        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
        </div>

        <h1>Your product</h1>

        <c:if test="${sessionScope.CART != null}">
            <c:if test="${not empty sessionScope.CART}">
                <table border="1" class="productTable">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Name</th>
                            <th>Image</th>                                     
                            <th>CategoryID</th>
                            <th>CreateDate</th>
                            <th>ExpireDate</th>
                            <th>Price</th>
                            <th>Quantity</th> 
                            <th>Delete</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${sessionScope.CART.getCart().values()}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.name}</td>
                                <td><image src="images/${dto.image}" class="productImage"/></td>
                                <td>${dto.categoryID}</td>
                                <td>${dto.createDate}</td>
                                <td>${dto.expireDate}</td>
                                <td>${dto.price}</td>
                                <td>
                                    <form action="UpdateCart" method="POST">
                                        <input type="hidden" name="txtProductID" value="${dto.productID}"/>
                                        <input type="number" name="txtQuantity" value="${dto.quantity}"/>
                                        <input type="submit" value="Update" onclick="return confirm('Are you sure ?')"/>
                                    </form>
                                </td>
                                <td>
                                    <form action="DeleteCart" method="POST">
                                        <input type="hidden" name="txtProductID" value="${dto.productID}">
                                        <input type="submit" value="Delete" onclick="return confirm('Are you sure ?')"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                Total:<font color="red">${sessionScope.TOTAL}</font><br/>
            </c:if>
        </c:if>

        <font color="red">${requestScope.NOTE}</font><br/>

        <c:if test="${sessionScope.TOTAL > 0}">
            <form action="Checkout">
                <select name="txtPayment">
                    <option value="cash">cash</option>
                    <option value="other">other</option>
                </select><br/>
                <input type="submit" value="Checkout" onclick="return confirm('Are you sure ?')"/>
            </form>
        </c:if>   
        <c:if test="${sessionScope.TOTAL == 0}">
            <p>Your cart is empty</p>
        </c:if>
    </body>
</html>
