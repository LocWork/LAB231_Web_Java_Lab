<%-- 
    Document   : productPage
    Created on : Oct 8, 2020, 6:10:57 PM
    Author     : hi
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Page</title>
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
                align-items: center;
                box-shadow: 0pt 0pt 30pt rgba(0,0,0,0.2);
                margin-bottom: 2%;
            }
            .menu a {
                margin-right: 12pt;
                font-size: 13pt;
                text-decoration: none;
            }
            .menu a:link, .menu a:visited {
                color: #f7f7c8;
            }

            .searchContainer{
                margin-bottom: 3%;
            }
            .searchBox{
                background-color: #ffffff;
                width: 100%;
                max-width: 200pt;
                padding: 10pt;
                border-radius: 25px;
                border: 2px solid #000000;
                margin: 0 auto;
            }
            .pagingContainer{
                margin-bottom: 3%;
                text-align: center;
            }
            .paging{
                width: 100%;
                margin-bottom: 3%;
            }

            .productContainer{
                margin-top: 2%;
                margin-bottom: 4;

            }

            .card{               
                float: left;
                margin: 1%;
                text-align: center;
                width: 30%;
                height: 400px;
                background-color: #ffffff;
                border: 2px solid #000000;
            }

            .productImage{
                padding-top: 3%;
                max-width: 100%;
                max-height: 200px;
            }
            .productContainer{
                display: block;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:if test="${requestScope.CATEGORY_LIST == null}">
            <c:redirect url="ProductPage"/>
        </c:if>
        <div class="menu">
            <a class="nav-link" href="home.jsp">Home</a>
            <c:if test="${sessionScope.USER_INFO == null}">        
                <a href="login.jsp">Login</a>
            </c:if>
            <c:if test="${sessionScope.CART != null}">
                <a href="viewCart.jsp">Cart</a>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID == 0}">
                    <a href="CreatePage">Create</a>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <c:if test="${sessionScope.USER_INFO.roleID != 2}">
                    <a href="LoadOrderID">Order</a>
                </c:if>
            </c:if>
            <c:if test="${sessionScope.USER_INFO != null}">
                <a href="Logout">Logout</a>
            </c:if>
        </div>



        <!-- Search form -->
        <div class="searchContainer">
            <div class="searchBox">
                <form action="Search" method="POST">
                    Name: <input type="text" name="txtSearchName"/><br/>  
                    <label for="rangeinput">Range</label><br>
                    From:   <input name="txtPriceMin" id="rangeinputFrom" type="range" min="0" max="200" value="0" onchange="rangevalueFrom.value = value"></input>
                    <output id="rangevalueFrom">0</output>$<br/>
                    To:     <input name="txtPriceMax" id="rangeinputTo" type="range" min="200" max="500" value="200" onchange="rangevalueTo.value = value"></input>
                    <output id="rangevalueTo">200</output>$<br/>
                    Category: 
                    <c:if test="${not empty requestScope.CATEGORY_LIST}">
                        <select name="txtCategoryID">
                            <c:forEach var="categoryName" items="${requestScope.CATEGORY_LIST}">
                                <option value="${categoryName.id}">${categoryName.name}</option>
                            </c:forEach>
                        </select>
                    </c:if><br/>
                    <input type="submit" name="action" value="Search"/><br/>
                </form>
            </div>
        </div>

        <div class="pagingContainer">
            <div class="paging">
                <c:if test="${requestScope.PAGE_INDEX != null && requestScope.PAGE_COUNT gt 1}">
                    <c:if test="${requestScope.PAGE_INDEX gt 1 && requestScope.PAGE_COUNT gt 1}">
                        <a href="Search?action=Previous&txtSearchName=${requestScope.SEARCH}&txtPageIndex=${requestScope.PAGE_INDEX}&txtPageCount=${requestScope.PAGE_COUNT}&txtPriceMin=${requestScope.PRICE_MIN}&txtPriceMax=${requestScope.PRICE_MAX}&txtCategoryID=${CATEGORY_ID}">Previous Page</a>
                    </c:if>

                    <c:if test="${requestScope.PAGE_INDEX lt requestScope.PAGE_COUNT && requestScope.PAGE_COUNT gt 1}">
                        <a href="Search?action=Next&txtSearchName=${requestScope.SEARCH}&txtPageIndex=${requestScope.PAGE_INDEX}&txtPageCount=${requestScope.PAGE_COUNT}&txtPriceMin=${requestScope.PRICE_MIN}&txtPriceMax=${requestScope.PRICE_MAX}&txtCategoryID=${CATEGORY_ID}">Next Page</a>
                    </c:if>
                </c:if>
            </div>
        </div>

        <div class="productContainer">
            <c:if test="${requestScope.PRODUCT_LIST != null}">
                <c:if test="${not empty requestScope.PRODUCT_LIST}"> 
                    <c:forEach var="dto" items="${requestScope.PRODUCT_LIST}">
                        <div class="card">
                            <a href="ProductInfo?txtProductID=${dto.productID}"><image src="images/${dto.image}" class="productImage"/><a/><br/>
                                <a href="ProductInfo?txtProductID=${dto.productID}">${dto.name}</a>
                                <p class="price">Price: ${dto.price}$</p>
                                <p>Category: ${dto.categoryID}</p>
                                <p>Quantity: ${dto.quantity}</p>
                        </div>
                    </c:forEach>
                </c:if>
            </c:if>
        </div>


    </body>
</html>
