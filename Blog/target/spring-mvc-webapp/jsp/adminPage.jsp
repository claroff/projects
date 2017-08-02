<%--
    Document   : adminPage
    Created on : Jul 22, 2017, 9:31:40 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Administrative Page</title>
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
            <h1>Admin Panel</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            <h2><a href="${pageContext.request.contextPath}/displayCreateStaticPageForm">Create new Static Page</a></h2>
            <h2><a href="${pageContext.request.contextPath}/blogPageCreate">Create new Blog Post</a></h2>
            <sec:authorize access="hasRole('ROLE_SUPERUSER')">
                <h2><a href="${pageContext.request.contextPath}/blogPageDisplayAll">View Active Blog Posts</a></h2>
                <h2><a href="${pageContext.request.contextPath}/blogPageDisplayAllInactive">View InActive Blog Posts</a></h2>
            </sec:authorize>
            <h2><a href="${pageContext.request.contextPath}/displayAdminCreateUser">Create new User</a></h2>
            <h2><a href="${pageContext.request.contextPath}/displayManageUser">Manage User</a></h2>
            <h2><a href="${pageContext.request.contextPath}/adminCreateNewCategory">Create New Category</a></h2>
            <h2><a href="${pageContext.request.contextPath}/displayManageCategory">Manage Categories</a></h2>
            <h2><a href="${pageContext.request.contextPath}/displayAllPostsByCategory">View all Posts by Category</a></h2>

        </div>
        <jsp:include page="footer.jsp"/>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>