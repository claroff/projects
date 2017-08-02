<%-- 
    Document   : manageCategory
    Created on : Jul 26, 2017, 7:23:13 PM
    Author     : apprentice
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Delete existing User</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="build.css">

        <style>
            /*Has to be internal, bootstrap is overriding due to specificity*/
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Manage Category</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>

            <div class="col-md-6"> 
                <table id="userTable" class="table table-hover">
                    <tr>

                        <th width="50%">Category Name</th>
                        <th width="25%">Edit Category</th>
                        <th width="25%">Delete Category</th>
                    </tr>
                    <c:forEach var="category" items="${categoryList}">
                        <tr>
                            <td>
                                <c:out value="${category.categoryName}"></c:out>
                                </td> 
                                <td>
                                    <a href="adminEditCategory?categoryID=${category.categoryID}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="deleteCategory?categoryID=${category.categoryID}" onclick="return confirm('Delete Category?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>