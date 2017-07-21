<%--
    Document   : editMetaHuman
    Created on : Jul 12, 2017, 11:09:14 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Organization</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Create Organization</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>




            <form class="form-horizontal" role="form" id="edit-dvd-form"
                  action="createOrganization" method="POST">
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Organization name:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <input class="form-control"
                               id="edit-MHname-input"
                               type="text"
                               placeholder="Organization Name"
                               name="name"
                               />
                    </div>
                </div>
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Description:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <input class="form-control"
                               id="edit-identity-input"
                               type="text"
                               placeholder="Organization Description"
                               name="description"/>
                    </div>
                </div>

                <label for ="choose-loc-list" class="col-sm-2"><h3>Location:</h3></label>
                <div class="form-group" col-sm-3 id="choose-loc-list">
                    <select class="form-control" id="MH-dropdown" name="location">
                        <option value="" id="default"></option>
                        <c:forEach var="currentLocation" items="${locationList}">
                            <option value="${currentLocation.locationID}" id="title">${currentLocation.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <label for ="choose-MH-list" class="col-sm-2"><h3>Affiliated MetaHuman(s):</h3></label>
                <div class="form-group" col-sm-3 id="choose-MH-list">
                    <select class="form-control" id="MH-dropdown" name="metaHuman">
                        <option value="" id="default"></option>
                        <c:forEach var="currentMetaHuman" items="${metaHumanList}">
                            <option value="${currentMetaHuman.metaHumanID}" id="title">${currentMetaHuman.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div id="MH-list-append"></div>
                <span id="add-MH-list"><a>Add another MetaHuman</a></span>


                <div class="col-md-offset-4 col-md-4">
                    <input type="hidden" path="organizationID"/>
                </div>

                <div class="col-sm-1">
                    <button class="btn btn-default"
                            id="save-edit-button"
                            type="submit">
                        Save Changes
                    </button>
                </div>
            </form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
    </body>
</html>