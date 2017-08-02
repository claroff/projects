<%-- 
    Document   : adminCreateNewCategory
    Created on : Jul 26, 2017, 7:08:00 PM
    Author     : apprentice
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create New Category</title>
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
            <h1>Create New Category</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            <div class="col-md-6">
                <form class="form-horizontal" 
                      role="form" method="POST" 
                      action="createCategory">
                    <div class="form-group">
                        <label for="CategoryName" class="col-md-4 control-label">Category Name:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" name="CategoryName" placeholder="Category Name" required=""/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default" value="Create New Category"/>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </body>
</html>