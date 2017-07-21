<%--
    Document   : editMetaHuman
    Created on : Jul 12, 2017, 11:09:14 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit MetaHuman</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit MetaHuman</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>


            <sf:form class="form-horizontal" role="form" id="edit-dvd-form" modelAttribute="metaHuman"
                     action="editMetaHuman" method="POST">
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>MetaHuman name:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-MHname-input"
                                  type="text"
                                  placeholder="MetaHuman Name"
                                  path ="name"
                                  />
                    </div>
                </div>
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Identity:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="text"
                                  placeholder="MetaHuman Identity"
                                  path="identity"/>
                    </div>
                </div>

                <label for ="choose-organization-list" class="col-sm-2"><h3>Affiliated Organization(s):</h3></label>
                <c:forEach var="currentMHOrg" items="${metaHumanOrgList}">
                    <div class="form-group" id="choose-organization-list">
                        <div class="col-sm-3">
                            <select class="form-control" id="category-dropdown" name="orgSelect">
                                <option selected="selected" hidden value="${currentMHOrg.organizationID}">${currentMHOrg.name}</option>
                                <option value="0" id="default">REMOVE</option>
                                <c:forEach var="currentOrganization" items="${organizationList}">
                                    <option value="${currentOrganization.organizationID}" id="title">${currentOrganization.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty metaHumanOrgList}">
                    <div class="form-group" id="choose-organization-list">
                        <div class="col-sm-3">
                            <select class="form-control" id="category-dropdown" name="orgSelect">
                                <option value="0" id="default">REMOVE</option>
                                <c:forEach var="currentOrganization" items="${organizationList}">
                                    <option value="${currentOrganization.organizationID}" id="title">${currentOrganization.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
                <div id="org-list-append"></div>
                <span id="add-organization-list"><a>Add another organization</a></span>

                <label for ="choose-power-list" class="col-sm-2"><h3>Power(s):</h3></label>
                <c:forEach var="currentPow" items="${metaHumanPowList}">
                    <div class="form-group" id="choose-power-list">
                        <div class="col-sm-3">
                            <select class="form-control" id="category-dropdown" name="powSelect">
                                <option selected="selected" hidden value="${currentPow.powerID}">${currentPow.description}</option>
                                <option value="0" id="default">REMOVE</option>
                                <c:forEach var="currentPower" items="${powerList}">
                                    <option value="${currentPower.powerID}" id="title">${currentPower.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:forEach>
                <div id="pow-list-append"></div>
                <span id="add-power-list"><a>Add another power</a></span>

                <div class="col-md-offset-4 col-md-4">
                    <sf:input type="hidden" path="metaHumanID"/>
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
        <script src="${pageContext.request.contextPath}/js/home.js"></script>
    </body>
</html>