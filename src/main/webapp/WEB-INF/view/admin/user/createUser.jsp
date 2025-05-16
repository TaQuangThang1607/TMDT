<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Create User</title>
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });

                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body>
            <div class="container mt-5">
                <h2 class="mb-4">Create New User</h2>
                <form:form action="/admin/create/user" method="post" modelAttribute="user" class="row g-3" enctype="multipart/form-data">
                    <div class="col-md-6">
                        <label class="form-label">Email</label>
                        <form:input type="text" path="email" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Password</label>
                        <form:input type="password" path="password" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Full Name</label>
                        <form:input type="text" path="fullName" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Phone</label>
                        <form:input type="text" path="phone" cssClass="form-control" />
                    </div>

                    <div class="col-12">
                        <label class="form-label">Address</label>
                        <form:input type="text" path="address" cssClass="form-control" />
                    </div>

                    <div class="col-21">
                        <label for="" class="form-label">Role</label>
                        <form:select class="form-select" path="role.name">
                            <form:option value="ADMIN">ADMIN</form:option>
                            <form:option value="USER">USER</form:option>
                        </form:select>
                    </div>

                    <div class="mb-3 col-12 col-md-6">
                        <label for="avatarFile" class="form-label">Avatar: </label>
                        <input class="form-control" type="file" id="avatarFile"
                                                    accept=".png, .jpg, .jpeg" name="getFileImage" />
                    </div>
                    <div class="col-12 mb-3">
                            <img src="" alt="avatar preview"
                                style="max-height: 250px; display: none;" id="avatarPreview" />
                            </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-success">Đăng ký</button>
                    </div>
                </form:form>
            </div>

            <!-- Bootstrap JS (Optional) -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>