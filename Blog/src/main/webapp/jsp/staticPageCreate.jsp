<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Static Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <script src='https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=8gb9pxvd80c26aaq2nzqgom5o56s9nrg4jjh6hz6sjlbgbmw'></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/tinymce.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#uploadImage").on("change", function () {
                    var formData = new FormData($('#create-blog-post-form')[0]);
                });
                tinymce.init({
                    selector: '#staticPageContent',
                    height: 300,
                    width: '100%',
                    theme: 'modern',
                    images_upload_url: '/CapStone/uploadStaticImage',
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
                        {
                            title: 'Test tem            plate 1', content: 'Test 1'},
                        {title: 'Test template 2', content: 'Test 2'}
                    ]
                });

                tinymce.init({
                    selector: '#staticPageContent',
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
                    toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright ali            gnjustify | bullist numlist outdent indent | link image',
                    toolbar2: 'print preview            media | forecolor backcolor emoticons | codesample',
                    image_advtab: true,
                    templates: [
                        {
                            title: 'Test template 1', content: 'Test 1'},
                        {
                            title: 'Test template 2', content: 'Test 2'}
                    ]
                });
            });
        </script>

        <style>
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Create Static Page</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>
        </div>

        <div class="container">
            <div class="row col-md-12">
                <div class="col-md-10 col-md-offset-1">
                    <br/>
                    <br/>

                    <form class="form-horizontal"
                          role="form"
                          id="create-staticPage-form"
                          action="createStaticPage"
                          method="POST">
                        <div class="form-group">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for ="add-staticPage-title" class="col-sm-4"> <h3>Static Page Title:</h3>  </label>
                                    <div class="col-md-10">
                                        <input class="form-control"
                                               id="add-static-title"
                                               name="staticPageName"
                                               type="text"
                                               placeholder="Static Page Title"
                                               required
                                               >
                                        <textarea id="staticPageContent"
                                                  name="staticPageContent">
                                        </textarea>
                                        <input name="file"
                                               type="file"
                                               id="uploadImage"
                                               class="hidden"
                                               >
                                        <!--IMAGE CONTENT-->
                                        <input name="file"
                                               type="file"
                                               id="uploadImage"
                                               class="hidden" >
                                    </div>
                                </div>

                                <input class="form-control"
                                       id="decide-nav-index"
                                       type="number"
                                       placeholder="Place in nav index"
                                       name="navIndex"
                                       required/>
                                <button class="btn btn-default"
                                        id="save-staticPage-button"
                                        type="submit">
                                    Create Page
                                </button>
                                </form>

                                <form action="${pageContext.request.contextPath}/displayAdminPage" method="GET">
                                    <button class="btn btn-default"
                                            id="cancel-staticPage-creation-button"
                                            type="submit"
                                            >
                                        Cancel
                                    </button>
                                </form>
                            </div>
                        </div>
                </div>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>

        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
