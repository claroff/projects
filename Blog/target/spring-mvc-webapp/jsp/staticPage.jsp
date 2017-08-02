<%--
    Document   : viewStaticPage
    Created on : Jul 19, 2017, 3:16:11 PM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <title>STATIC PAGE TEST</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <style>
            /*Has to be internal, bootstrap is overriding due to specificity*/
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>${staticPage.staticPageName}</h1>
            <hr/>
            <div class="navbar">

                <jsp:include page="nav.jsp"/>

            </div>
            <h2>${staticPage.staticPageName}</h2>

            ${staticPage.staticPageContent}

            <sec:authorize access="hasRole('ROLE_ADMIN')">

                <span value="displayEditStaticPageForm" method="GET"><a href="displayEditStaticPageForm?staticPageID=${staticPage.staticPageID}">
                        Edit</a></span>
                <span value="deleteStaticPage" method="GET"><a href="deleteStaticPage?staticPageID=${staticPage.staticPageID}" onclick="return confirm('Are you sure you want to delete?')">
                        Delete
                    </a></span>

            </sec:authorize>
        </div>
        <jsp:include page="footer.jsp"/>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
