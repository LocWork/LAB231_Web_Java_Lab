<%-- 
    Document   : productInfoPage
    Created on : Oct 10, 2020, 3:57:30 PM
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
            .card {
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
                background-color: #ffffff;
                max-width: 500px;
                margin: 4%;
                text-align: center;
                font-family: arial;
            }
            .price {
                color: grey;
                font-size: 22px;
            }

            .productImage{
                max-width: 300px;
                max-height: 300px;
            }



            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                background-color: #f2f2f2;
                height: 100%;

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

            .container{
                margin: 0 auto;
            }

            .inputContainer{
                margin: 0 auto;
                padding: 4%;
            }

            .midContainer{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: 2px solid #000000;
                max-width: 40%;
                text-align: center;
            }

        </style>
        <title>Product Information Page</title>
    </head>
    <body>

        <c:if test="${requestScope.PRODUCT_INFO == null}">
            <c:redirect url="ProductPage"/>
        </c:if>

        <c:if test="${requestScope.PRODUCT_INFO.status != 'active'}">
            <c:redirect url="ProductPage"/> 
        </c:if> 

        <c:if test="${requestScope.PRODUCT_INFO.quantity < 1}">
            <c:redirect url="ProductPage"/> 
        </c:if> 

        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
            <c:if test="${sessionScope.USER_INFO == null}">
                <a href ="login.jsp">Login</a><br/>
            </c:if>
            <c:if test="${sessionScope.CART != null}">
                <a href ="viewCart.jsp">Cart</a><br/>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID == 0}">
                    <a href="UpdatePage?txtProductID=${requestScope.PRODUCT_INFO.productID}">Update Product</a><br/>
                </c:if>
            </c:if> 
        </div>



        <div class="mainContainer">
            <div class="midContainer">
                <c:if test="${requestScope.PRODUCT_INFO != null}">
                    <c:if test="${requestScope.PRODUCT_INFO.status == 'active'}">
                        <div class="container">
                            <div class="card">
                                <image src="images/${requestScope.PRODUCT_INFO.image}" class="productImage"/><br>
                                <p>Name: ${requestScope.PRODUCT_INFO.name}</p>
                                <p class="price">Price: ${requestScope.PRODUCT_INFO.price}$</p>
                                <p>CreateDate: ${requestScope.PRODUCT_INFO.createDate}</p>
                                <p>ExpireDate: ${requestScope.PRODUCT_INFO.expireDate}</p>
                                <p>CategoryID: ${requestScope.PRODUCT_INFO.categoryID}</p>
                                <p>Quantity: ${requestScope.PRODUCT_INFO.quantity}</p>
                                <p>Text: ${requestScope.PRODUCT_INFO.description}</p>
                            </div>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                    <c:if test="${requestScope.PRODUCT_INFO.quantity > 0}" >
                        <div class="inputContainer">
                            <form action="AddCart" method="POST">
                                <input type="hidden" value="${requestScope.PRODUCT_INFO.productID}" name="txtProductID"/>
                                <input type="number" name="txtProductQuantity"/>
                                <input type="submit" value="Set"/>
                            </form>
                            <font color="red">${requestScope.NOTE}</font><br/> 
                        </div>
                    </c:if>
                </c:if>
            </div>  
        </div>
    </body>
</html>
