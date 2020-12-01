<%-- 
    Document   : createProduct
    Created on : Oct 10, 2020, 5:38:47 PM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
            .createBox{
                margin: 0 auto;
                background-color: #ffffff;
                border-radius: 25px;
                border: 2px solid #000000;
                max-width: 40%;
                text-align: center;

            }
            .createForm{
                margin: 0 auto;
                text-align: left;
                max-width: 70%;
            }
            .btn{
                margin-top: 4%;
                text-align: center;
                padding-bottom: 3%;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.USER_INFO == null}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                <c:redirect url="home.jsp"/>
            </c:if>
        </c:if>
        <c:if test="${requestScope.CATEGORY_LIST == null}">
            <c:redirect url="CreatePage"/>
        </c:if>
        <div class="menu">
            <a href="home.jsp">Home Page</a>
            <a href="ProductPage">Product</a> 
        </div>
        <div class="createContainer">
            <div class="createBox">
                <h1>Create Form</h1>
                <div class="createForm">                   
                    <form action="CreateProduct" method="POST" class="productForm" enctype="multipart/form-data">
                        Name: <input type="text" name="txtName" value="${requestScope.PRODUCT_INFO.name}"/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.name}</font><br/>
                        Price: <input type="number" step="0.0001" name="txtPrice" value="${requestScope.PRODUCT_INFO.price}"/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.price}</font><br/>
                        Description: <input type="text" name="txtDescription" value="${requestScope.PRODUCT_INFO.description}"/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.description}</font><br/>
                        Quantity: <input type="number" name="txtQuantity" value="${requestScope.PRODUCT_INFO.quantity}"/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.quantity}</font><br/>
                        Image: <input type="file" name="txtImage" accept="image/*"/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.image}</font><br/>
                        CreateDate: <input type="date" name="txtCreateDate" min="2018-01-01" max="2022-12-31" ${requestScope.PRODUCT_INFO.createDate}/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.createDate}</font><br/>
                        ExpireDate: <input type="date" name="txtExpireDate" min="2018-01-01" max="2024-12-31" ${requestScope.PRODUCT_INFO.expireDate}/><br/>
                        <font color ="red">${requestScope.ERROR_PRODUCT.expireDate}</font><br/>
                        Category: 
                        <c:if test="${not empty requestScope.CATEGORY_LIST}">
                            <select name="txtCategoryID">
                                <c:forEach var="categoryName" items="${requestScope.CATEGORY_LIST}">
                                    <option value="${categoryName.id}">${categoryName.name}</option>
                                </c:forEach>
                            </select>
                        </c:if><br/>
                        <div class="btn">
                            <input type="submit" name="action" value="Create"/><br/>
                            <font color ="red">${requestScope.NOTE}</font><br/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
