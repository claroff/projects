<%--
    Document   : blogPage
    Created on : Jul 21, 2017, 1:32:16 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/home.css">

        <title>home</title>
        <style>
            /*Has to be internal, bootstrap is overriding due to specificity*/
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <!--TODO-->
    <!--Need to add Edit and Delete Still for Admin/User-->
    <body>
        <h1>Blog Page</h1>
        <hr/>
        <div class="container">
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
        </div>
        <!--Link below was purely for a test link, we will change this-->
        <div class="container">
            <li role="presentation" ><a href="${pageContext.request.contextPath}/blogPageCreate">Blog Page Create</a></li>
        </div>
        <div class="container">
            <!--            jsp:include page="header.jsp"/>-->
            <!--Beginning of BlogView-->
            <div class="row col-md-12">
                <div class="col-md-10 col-md-offset-1">
                    <div id="viewBlogPosts"></div>

                    <!--Need something else other than the forEach. Not sure what though-->

                    <c:forEach items="${displayBlogPosts}" var="blogPost">
                        <table class="table table-bordered" id="blogPost-row-${blogpost.id}">
                            <tr>
                                <td id="blogTitle">
                                    <h2>${blogPost.title}</h2><br/>
                                    <span>${blogPost.postDate}${blogPost.author}${blogPost.categoryName}</span>
                                </td>
                            </tr>

                            <tr><td>${blogPost.content}</td></tr>
                        </table>
                        <br/><br/>
                    </c:forEach>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>
    </div>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>