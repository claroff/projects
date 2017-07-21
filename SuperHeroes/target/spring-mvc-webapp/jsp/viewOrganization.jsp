
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
            <h1>Organization Details</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>

            <h2>Organization name: ${organization.name}</h2>
            <h2>Description: ${organization.description}</h2>
            <h2>Location:
                <span value="displayLocationInfo" method = "GET"><a href="displayLocationInfo?locationId=${organization.location.locationID}">
                        ${organization.location.name}</a></span></h2></h2>
        <h2>Affiliated MetaHuman(s): |
            <c:forEach var="currentMetaHuman" items="${metaHumanList}">
                <span value="displayMetaHumanInfo" method = "GET"><a href="displayMetaHumanInfo?metaHumanId=${currentMetaHuman.metaHumanID}">
                        ${currentMetaHuman.name}</a> | </span>
                    </c:forEach>
        </h2>

    </div>

    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
