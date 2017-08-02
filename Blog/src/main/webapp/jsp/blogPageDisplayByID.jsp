
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Blog Post</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <style>
            /*Has to be internal, bootstrap is overriding due to specificity*/
            h1 {
                text-align: center;
            }

            #blogPageContent {
                text-align: center;
            }
        </style>

        <script>
            tinymce.init({
                selector: '#commentText',
                height: 300,
                width: '100%',
                theme: 'modern',
                plugins: [
                    'advlist autolink lists image link charmap print preview hr anchor pagebreak',
                    'searchreplace wordcount visualblocks visualchars code fullscreen',
                    'insertdatetime media nonbreaking save table contextmenu directionality',
                    'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc'
                ],
                toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
                toolbar2: 'print preview media | forecolor backcolor emoticons | codesample',
                image_advtab: true,
                templates: [
                    {title: 'Test template 1', content: 'Test 1'},
                    {title: 'Test template 2', content: 'Test 2'}
                ]
            });
        </script>
    </head>
    <body>
        <div class="container">
            <h1>${blogPostByID.title}</h1>
            <hr/>
            <div class="navbar">

                <jsp:include page="nav.jsp"/>

            </div>
            <div class="row"
                 id="blogPageContent">

                <h2>${blogPostByID.title}</h2>
                <br>
                <h3>${blogPostByID.user.userName}</h3>
                <h3>${blogPostByID.startDate}</h3>
                <br>
                <p>${blogPostByID.blogContent}</p>
                <br>
                <!--            <p>$        </p> blogPostByID.tags can't utilize tags yet MB -->
                <br>
            </div>
            <div class="row">
                <sec:authorize access="hasRole('ROLE_USER')">
                    <form class="form-horizontal"
                          role="form" method="POST"
                          action="postComment">
                        <input type="hidden" name="userName" value="${pageContext.request.userPrincipal.name}">
                        <input type="hidden" name="blogPostID" value="${blogPostByID.blogPostID}">

                        <div class="form-group">
                            <label for="Comment" class="col-md-1 control-label">Comment:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="Comment" placeholder="Leave comment here.." required=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-offset-1 col-md-8">
                                <input type="submit" class="btn btn-default" value="Post Comment"/>
                            </div>
                        </div>
                    </form>
                </sec:authorize>
            </div>


        </div>
        <div class="container" name="commentDiv">
            <h1 style="text-align:left">Post Comments</h1>
            <table>
                <th style="width:30%">User</td>
                <th style="width:20%">Comment Date</td>
                <th style="width:50%">Comment</td>

                    <c:forEach var="comment" items="${commentList}">
                    <tr>
                        <td>
                            <c:out value="${comment.user.userName}"></c:out>
                            </td>
                            <td>
                            <c:out value="${comment.commentDate}"></c:out>
                            </td>
                            <td>
                            <c:out value="${comment.content}"></c:out>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>