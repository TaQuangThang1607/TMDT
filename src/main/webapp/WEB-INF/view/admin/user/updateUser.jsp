<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Update User</title>
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        </head>

        <body>
            <div class="container mt-5">
                <h2 class="mb-4">Update User</h2>

                <form:form action="/admin/user/update" method="post" modelAttribute="updateUser" class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">ID</label>
                        <form:input type="text" path="id" cssClass="form-control" readonly="true" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email</label>
                        <form:input type="email" path="email" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Password</label>
                        <form:input type="text" path="password" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Full Name</label>
                        <form:input type="text" path="fullName" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Phone</label>
                        <form:input type="text" path="phone" cssClass="form-control" />
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Address</label>
                        <form:input type="text" path="address" cssClass="form-control" />
                    </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">Update User</button>
                    </div>
                </form:form>
            </div>

            <!-- Bootstrap JS (optional) -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>