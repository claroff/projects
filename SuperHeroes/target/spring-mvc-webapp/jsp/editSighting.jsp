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
        <title>Edit Sighting</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit Sighting</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>

            <sf:form class="form-horizontal" role="form" id="edit-sighting-form" modelAttribute="sighting"
                     action="editSighting" method="POST">
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Sighting name:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-loc-name-input"
                                  type="text"
                                  placeholder="Sighting Name"
                                  path ="name"
                                  />
                    </div>
                </div>
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Date:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="date"
                                  value="${sighting.date}"
                                  path="date"/>
                    </div>
                </div>

                <label for ="choose-loc-list" class="col-sm-2"><h3>Location:</h3></label>
                <div class="form-group" id="choose-loc-list">
                    <div class="col-sm-3">
                        <select class="form-control" id="category-dropdown" name="locSelect">
                            <option selected="selected" hidden value="${sighting.location.locationID}">${sighting.location.name}</option>
                            <option value="" id="default"></option>
                            <c:forEach var="currentLocation" items="${locationList}">
                                <option value="${currentLocation.locationID}" id="title">${currentLocation.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <label for ="choose-MH-list" class="col-sm-2"><h3>MetaHuman(s) Involved:</h3></label>
                <c:forEach var="currentMetaHuman" items="${sightingMetaHumanList}">
                    <div class="form-group" id="choose-MH-list">
                        <div class="col-sm-3">
                            <select class="form-control" id="category-dropdown" name="MHselect">
                                <option selected="selected" hidden value="${currentMetaHuman.metaHumanID}">${currentMetaHuman.name}</option>
                                <option value="" id="default"></option>
                                <c:forEach var="currentMH" items="${metaHumanList}">
                                    <option value="${currentMH.metaHumanID}" id="title">${currentMH.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:forEach>

                <div id="MH-list-append"></div>
                <span id="add-MH-list"><a>Add another MetaHuman</a></span>


                <div class="col-md-offset-4 col-md-4">
                    <sf:input type="hidden" path="sightingID"/>
                </div>

                <div class="col-sm-1">
                    <button class="btn btn-default"
                            id="save-edit-button"
                            type="submit">
                        Save Changes
                    </button>
                </div>
            </sf:form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>