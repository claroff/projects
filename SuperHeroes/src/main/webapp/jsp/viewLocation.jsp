<%--
    Document   : viewMetaHuman
    Created on : Jul 12, 2017, 10:57:31 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <h1>Location Details</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>

            <h2>Location name: ${location.name}</h2>
            <h2>Description: ${location.description}</h2>
            <h2>Address: ${location.address}</h2>
            <h2>Latitude: ${location.latitude}</h2>
            <h2>Longitude: ${location.longitude}</h2>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
