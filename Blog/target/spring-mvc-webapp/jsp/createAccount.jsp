<%--
    Document   : createUser
    Created on : Jul 22, 2017, 2:53:32 PM
    Author     : apprentice
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create New User</title>
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
            <h1>Create New User</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            <h2>Create new User?</h2>

            <div class="col-md-6">
                <h2>Add New User</h2>
                <form class="form-horizontal"
                      role="form" method="POST"
                      action="createAccount">
                    <div class="form-group">
                        <label for="UserName" class="col-md-4 control-label">UserName:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" name="UserName" placeholder="UserName" required=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Password" class="col-md-4 control-label">Password:</label>
                        <div class="col-md-8">
                            <input type="password" class="form-control" name="Password" placeholder="Password" required=""/>
                        </div>
                    </div>


                    <input name="AdminRadio"  value="False" hidden>
                    <input name="SuperUserRadio"  value="False" hidden>


                    <div class="form-group">
                        <label for="Image" class="col-md-4 control-label">Image:</label>
                        <div class="col-md-8">
                            <label class="custom-file control-label">
                                <select name="Image">
                                    <option value="${defaultImage.imageID}" selected="" disabled="" >
                                        <c:forEach var="image" items="${imageList}">
                                        <option value="${image.imageID}" selected><c:out value="${image.imageName}"></c:out></option>
                                    </c:forEach>

                                    </option>
                                </select>
                                <span class="custom-file-control"></span>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default" value="Create New User"/>
                        </div>
                    </div>
                </form>

            </div>
    </body>
</html>
