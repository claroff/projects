<%--
    Document   : viewMetaHuman
    Created on : Jul 12, 2017, 10:57:31 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>MetaHuman Details</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>

            <h2>MetaHuman name: ${metaHuman.name}</h2>
            <h2>Identity: ${metaHuman.identity}</h2>
            <h2>Affiliated Organization(s): |
                <c:forEach var="currentOrganization" items="${organizationList}">
                    <span value="displayOrganizationInfo" method = "GET"><a href="displayOrganizationInfo?organizationId=${currentOrganization.organizationID}">
                            ${currentOrganization.name}</a> | </span>
                        </c:forEach>
            </h2>
            <h2>Power(s): |
                <c:forEach var="currentPower" items="${powerList}">
                    ${currentPower.description} |
                </c:forEach>
            </h2>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
