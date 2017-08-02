<%-- 
    Document   : blogPageDisplayAllInactive
    Created on : Jul 24, 2017, 3:35:14 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--CHANDLER/MIKE home controller needs hooked up so that it will display what is below.
Database is populated, so we should be able to test it. -->

<html>
    <head>
        <title>Blog Posts</title>
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
            <h1>InActive Blogs</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
        </div>
        <div class="container">
            <!-- Main Page Content Start -->
            <div class="row">
                <div class="col-md-12">
                    <br>
                    <table id="blogPostTable" class="table table-hover">
                        <tr>
                            <th width="25%">Blog ID</th>
                            <th width="25%">Blog Title</th>
                            <th width="25%">User ID</th>
                            <th width="25%">Date Name</th>
                        </tr>
                        <c:forEach var="blogPosts" items="${blogPostsList}">
                            <tr>
                                <td>
                                    <a href="blogPageDisplayByID?blogPostID=${blogPosts.blogPostID}">
                                        <c:out value="${blogPosts.blogPostID}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${blogPosts.title}"/>
                                </td>
                                <td>
                                    <c:out value="${blogPosts.user.userName}"/>
                                </td>
                                <td>
                                    <c:out value="${blogPosts.startDate}"/>
                                </td>
                                <td>
                                    <a href="displayAdminUpdateBlogPostPage?blogPostID=${blogPosts.blogPostID}">
                                        Edit
                                    </a>

                                </td>
                                <td>
                                    <a href="adminSetBlogPostActive?blogPostID=${blogPosts.blogPostID}">
                                        Set Active
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>                    
                </div> <!-- End col div -->
            </div>
        </div>
        <footer>
            <jsp:include page="footer.jsp"/>
        </footer>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
