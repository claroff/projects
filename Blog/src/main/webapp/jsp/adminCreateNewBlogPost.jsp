<%--
    Document   : AdminCreateNewBlogPost
    Created on : Jul 22, 2017, 12:31:24 PM
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

        <script src='https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=8gb9pxvd80c26aaq2nzqgom5o56s9nrg4jjh6hz6sjlbgbmw'></script>
        <script src="js/jquery-3.1.1.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#uploadImage").on("change", function () {
                    var formData = new FormData($('#create-blog-post-form')[0]);
                });

                tinymce.init({
                    selector: '#blogText',
                    height: 300,
                    width: '100%',
                    theme: 'modern',
                    images_upload_url: '/CapStone/uploadImage',
                    images_upload_base_path: '/CapStone',
                    file_picker_callback: function (callback, value, meta) {
                        if (meta.filetype === 'image') {
                            $("#uploadImage").click();
                            $("#uploadImage").on('change', function () {
                                var file = this.files[0];
                                var reader = new FileReader();
                                reader.onload = function (e) {
                                    callback(e.target.result, {
                                        alt: ''
                                    });
                                };
                                reader.readAsDataURL(file);
                            });
                        }
                    },
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
            });
        </script>
        <style>
            h1 {
                text-align: center;
            }

            .footer{
                margin-top: 20px;
            }
        </style>
        <title>Write Your Blog!</title>
    </head>
    <body>
        <div class="container">
            <h1>(Admin)Create New Blog</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>
        </div>

        <!--Beginning of Create Blog Page-->

        <div class="container">
            <div class="row col-md-12">
                <div class="col-md-10 col-md-offset-1">
                    <br/>
                    <br/>
                    <form class="form-horizontal"
                          role="form"
                          id="create-blog-post-form"
                          action="createNewBlogPost"
                          method="POST"
                          enctype="multipart/form-data" >
                        <!--Blog Title-->
                        <div class="form-group">
                            <label for="add-blog-title" class="col-md-2 control-label">Blog Title:</label>
                            <div class="col-md-5">
                                <input type="text"
                                       id="blogTitle"
                                       style="color:gray;"
                                       placeholder="Title of Blog Post"
                                       name="blogTitle"
                                       class="form-control"
                                       required="">
                            </div>
                        </div>
                        <!--Start Date-->
                        <div class="form-group">
                            <label for="add-start-date" class="col-md-2 control-label">Post Date:</label>
                            <div class="col-md-5">
                                <input type="date"
                                       id="startDate"
                                       style="color:gray;"
                                       placeholder="YYYYMMDD"
                                       name="startDate"
                                       class="form-control"
                                       required="">
                            </div>
                        </div>
                        <!--Expiration Date-->
                        <div class="form-group">
                            <label for="add-end-date" class="col-md-2 control-label">Expire Date:</label>
                            <div class="col-md-5">
                                <input type="date"
                                       id="endDate"
                                       style="color:gray;"
                                       placeholder="YYYYMMDD"
                                       name="endDate"
                                       class="form-control"
                                       required="">
                            </div>
                        </div>

                        <input type="text"
                               name="user.userID"
                               hidden
                               value="1"/>
                        <!--Blog Content-->
                        <div class="form-group">
                            <label for="add-blog-content" class="col-md-2 control-label">Blog Content:</label>
                            <div class="col-md-10">
                                <textarea id="blogText"
                                          name="blogContent"
                                          class="form-control"
                                          required="">
                                </textarea>

                                <!--Image Area-->
                                <input name="file"
                                       type="file"
                                       id="uploadImage"
                                       class="hidden" >
                            </div>
                        </div>

                        <!--ACTIVE FLAGGING SECTION-->
                        <input type="hidden"
                               name="isActive"
                               value="true">

                        <br>
                        <!--TAG SECTION-->
                        <div class="form-group">
                            <label for="add-create-tag" class="col-md-2 control-label">Create Tag:</label>
                            <div class="col-md-5">
                                <input type="text"
                                       id="tagName"
                                       placeholder="#Believeland"
                                       name="blogTag"
                                       class="form-control">
                            </div>
                        </div>
                        <!--Create Blog Button-->
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-8">
                                <input type="submit"
                                       id="add-blog-post"
                                       formmethod="POST"
                                       formaction="createNewBlogPost"
                                       value="Create Blog Post">
                            </div>
                        </div>
                    </form>
                    <!--CANCEL BUTTON-->
                    <form action="${pageContext.request.contextPath}/displayAdminPage" method="GET">
                        <div class="col-md-offset-2">
                            <button class="btn btn-default col-md-offset-2"
                                    id="cancel-blog-post-creation-button"
                                    type="submit">
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>
    </body>
    <script src="js/bootstrap.min.js"></script>
</html> <!-- this page isn't being used at all-->