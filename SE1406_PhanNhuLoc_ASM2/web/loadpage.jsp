<%-- 
    Document   : loadpage
    Created on : Nov 2, 2020, 10:41:36 AM
    Author     : hi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Load Page</title>
    </head>
    <body>
        
        <c:if test="${sessionScope.USER_INFO != null}">
            <c:if test="${sessionScope.USER_INFO.roleID != 0}">
                <c:redirect url="home.jsp"/>
            </c:if>
        </c:if>
        
        <c:if test="${ORDER_LIST != null}">
            <c:if test="${not empty ORDER_LIST}">
                <table border="1" class="orderTable">
                    <thead>
                        <tr>
                           
                            <th>userID</th>
                            <th>name</th>
                            <th>phone</th>
                            <th>address</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dto" items="${ORDER_LIST}">
                            <tr>
                                <td>${dto.userID}</td>
                                <td>${dto.name}</td>
                                <td>${dto.phone}</td>
                                <td>${dto.address}</td>
                               
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
        </c:if>
    </body>
</html>
