<%--
    Document   : adminDeleteUser
    Created on : Jul 22, 2017, 6:24:40 PM
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
            <h1>Manage Users</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            
            <div class="col-md-6">
                
                <table id="userTable" class="table table-hover">
                    <tr>
                        <th width="20%">Profile Picture</th>
                        <th width="20%">User Name</th>
                        <!--                        <th width="15%">Super User</th>
                                                <th width="15%">Administrator</th>-->
                        <th width="10%">Is Active</th>
                        <th width="15%">Edit User</th>
                        <th width="15%">Deactivate User</th>
                    </tr>
                    <c:forEach var="currentUser" items="${userList}">
                        <tr>
                            <td>
                                <img src="${pageContext.request.contextPath}/get-user-image-with-media-type/${currentUser.userID}"/></img>
                            </td>
                            <td>
                                <c:out value="${currentUser.userName}"/>
                            </td>
                            <!--                            <td>

                                                        </td>
                                                        <td>

                                                        </td>-->

                            <td>
                                <c:out value="${currentUser.isActive}"/>
                            </td>
                            <td>
                                <a href="adminEditUser?userID=${currentUser.userID}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="setUserInactive?userID=${currentUser.userID}"> Deactivate</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
    </body>
</html>
