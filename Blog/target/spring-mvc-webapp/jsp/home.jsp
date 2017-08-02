<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Index Page</title>
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
            <h1>My Blogs</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
            <!-- Picture Carousel -->
            <!--I Think this will work? BT-->
            <!--            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                                 Indicators
            c:forEach var="pictureCarousel" items="$/{pictureList}">
                Link for this=https://forums.asp.net/t/2065335.aspx?Carousel+populating+images+from+sql+database
                <ol class="carousel-indicators">
                    <li data-target="imageCarousel" data-slide-to="$/{pictureCarousel.imageID}" class="active"></li>
                </ol>
                 Wrapper for slides
                <div class="carousel-inner dl-horizontal" role="listbox">
                    <div class="item active">
                        <img src="$/{pictureCarousel.image}"  alt=@Html.DisplayFor(modelItem => item.AspNetUser.DisplayName) />
                    </div>
                </div>
                 Left and right controls
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            /c:forEach>
        </div>
    </div>-->
            <hr/>
            <sec:authorize access="hasRole('ROLE_SUPERUSER')">
                <!--            DO NOT DELETE, IMPORTANT TEMPLATE FOR UPLOAD-->
                <form role="form" method="POST" action="saveImage" enctype="multipart/form-data">
                    <input type="file" name="file">
                    <input type="text" name="name">
                    <input type="submit" class="btn btn-default btn-primary" />
                </form>
            </sec:authorize>

            <div class="container">
                <div class="row col-md-12">
                    <div class="col-md-10 col-md-offset-1">
                        <div id="viewBlogPosts">




                            <!--ATTENTION: Can't use a FOR EACH, Not sure which one to use-->



                            <c:forEach items="${blogPostList}" var="blogPost">
                                <table class="table table-bordered" id="blogPost-row-${blogPost.blogPostID}">
                                    <tr>
                                        <td id="blogTitle">
                                            <a href="blogPageDisplayByID?blogPostID=${blogPost.blogPostID}">
                                                <h2>${blogPost.title}</h2><br/>
                                            </a>
                                            <!--BlogPost or User??-->
                                            <span>
                                                ${blogPost.user.userName}
                                                <br>
                                                ${blogPost.startDate}
                                            </span>
                                        </td>
                                    </tr>
                                    <!--BlogPost or Image? Not sure how to implement this one-->
                                    <tr>

                                        <td><img src="${pageContext.request.contextPath}/get-image-with-media-type/${blogPost.blogPostID}"/></tr>
                                    <tr>
                                        <td>${blogPost.blogContent}</td>
                                    </tr>
                                    <br>
                                    <!--Not sure if this is correct either-->
                                    <!--                                    <tr>
                                    <td>{blogPost.tagID}</td>
                                    </tr>
                                    <br>
                                    <tr>
                                    <td>{blogPost.commentID}</td>
                                    </tr>-->
                                </table>
                                <hr/>
                                <br/><br/>
                            </c:forEach>
                        </div>
                    </div>
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

