<%-- 
    Document   : adminEditCategory
    Created on : Jul 26, 2017, 7:48:52 PM
    Author     : apprentice
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Category</title>
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
            <h1>Edit Category</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            
            <div class="col-md-6">

                <sf:form class="form-horizontal" role="form" modelAttribute="category"
                         action="editCategory" method="POST">
                    <div class="form-group">
                        <label for="categoryName" class="col-md-4 control-label">Category Name:</label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" path="categoryName" placeholder="Category Name" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default"/>
                        </div>
                    </div>
                    <sf:input type="hidden" path="categoryID"/>
                </sf:form>
                <h2><a href="${pageContext.request.contextPath}/displayManageCategory">Back to list page</a></h2>
            </div>
    </body>
</html>
