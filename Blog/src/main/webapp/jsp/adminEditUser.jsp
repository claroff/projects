<%--
    Document   : createUser
    Created on : Jul 22, 2017, 2:53:32 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit User</title>
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
            <h1>Edit User</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>


            <div class="col-md-6">

                <sf:form class="form-horizontal" role="form" modelAttribute="user"
                         action="editUser" method="POST">

                    <div class="form-group">
                        <label for="userName" class="col-md-4 control-label">UserName:</label>
                        <div class="col-md-8">
                            <sf:input type="text" class="form-control" path="userName" placeholder="UserName" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPassword" class="col-md-4 control-label">Password:</label>
                        <div class="col-md-8">
                            <sf:input type="password" class="form-control" path="userPassword" placeholder="Password" required="required"/>
                        </div>
                    </div>
                    <c:set var="isAdminCheck" value="${isAdmin}"/>
                    <div class="form-group">
                        <label for="isAdmin" class="col-md-4 control-label">Is Administrator:</label>
                        <div class="col-md-8">
                            <label class="custom-control custom-radio">
                                <input type="radio" id="radio1" name="AdminRadio" value="true" class="custom-control-input" <c:if test="${isAdminCheck == true}">checked</c:if>/>

                                    <span class="custom-control-indicator"></span>
                                    <span class="custom-control-description">True</span>
                                </label>
                                <label class="custom-control custom-radio">
                                    <input type="radio" id="radio2" name="AdminRadio" value="false" <c:if test="${isAdminCheck == false}">checked</c:if> class="custom-control-input"/>
                                    <span class="custom-control-indicator"></span>
                                    <span class="custom-control-description">False</span>
                                </label>

                            </div>
                        </div>
                    <c:set var="isSuperUserCheck" value="${isSuperUser}"/>
                    <div class="form-group">
                        <label for="isSuperUser" class="col-md-4 control-label">Is Super User:</label>
                        <div class="col-md-8">
                            <label class="custom-control custom-radio">

                                <input type="radio" id="radio3" name="SuperUserRadio" class="custom-control-input" <c:if test="${isSuperUserCheck == true}">checked</c:if> value="true"/>

                                    <span class="custom-control-indicator"></span>
                                    <span class="custom-control-description">True</span>
                                </label>
                                <label class="custom-control custom-radio">
                                    <input type="radio" id="radio4" name="SuperUserRadio" class="custom-control-input" <c:if test="${isSuperUserCheck == false}">checked</c:if> value="false"/>
                                    <span class="custom-control-indicator"></span>
                                    <span class="custom-control-description">False</span>
                                </label>
                            </div>
                        </div>

                    <c:set var="isActiveCheck" value="${user.isActive}"/>
                    <div class="form-group">
                        <label for="isActive" class="col-md-4 control-label">Is Active:</label>
                        <div class="col-md-8">
                            <label class="custom-control custom-radio">
                                <c:if test="${isActiveCheck!='0'}">
                                    <sf:radiobutton id="radio3" name="isActiveRadio" path="isActive" class="custom-control-input" checked="checked" value="true"></sf:radiobutton>

                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">True</span>
                                    </label>
                                    <label class="custom-control custom-radio">
                                    <sf:radiobutton id="radio4" name="isActiveRadio" path="isActive" class="custom-control-input" value="false"></sf:radiobutton>
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">False</span>
                                    </label>
                            </c:if>
                            <c:if test="${isActiveCheck=='0'}">

                                <label class="custom-control custom-radio">
                                    <sf:radiobutton id="radio3" name="isActiveRadio" path="isActive" class="custom-control-input" value="true"></sf:radiobutton>

                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">True</span>
                                    </label>
                                <sf:radiobutton id="radio4" name="isActiveRadio" path="isActive" class="custom-control-input" checked="checked" value="false"></sf:radiobutton>

                                    <span class="custom-control-indicator"></span>
                                    <span class="custom-control-description">False</span>
                                    </label>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="Image" class="col-md-4 control-label">Image:</label>
                        <div class="col-md-8">
                            <label class="custom-file control-label">
                                <sf:select path="image.imageID">
                                    <option value="imageID" selected="" disabled="" >
                                        <c:forEach var="image" items="${imageList}">
                                        <option value="${image.imageID}"><c:out value="${image.imageName}"></c:out></option>
                                    </c:forEach>

                                    </option>
                                </sf:select>
                                <span class="custom-file-control"></span>
                            </label>
                        </div>
                    </div>

                    <sf:input type="hidden" path="userID"/>

                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default"/>
                        </div>
                    </div>


                </sf:form>

                <a class="btn btn-default btn-primary" href="${pageContext.request.contextPath}/displayManageUser">Back to list</a>

            </div>
    </body>
</html>
