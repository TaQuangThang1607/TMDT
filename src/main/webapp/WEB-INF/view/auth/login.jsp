<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Login - SB Admin</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
                    integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
                    crossorigin="anonymous">
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="bg-primary">
                <div id="layoutAuthentication">
                    <div id="layoutAuthentication_content">
                        <main>
                            <div class="container">
                                <div class="row justify-content-center">
                                    <div class="col-lg-5">
                                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                                            <div class="card-header">
                                                <h3 class="text-center font-weight-light my-4">Login</h3>
                                            </div>
                                            <div class="card-body">
                                                <form method="post" action="/login">
                                                    <c:if test="${param.error != null}">
                                                        <div class="my-2" style="color: red;">Invalid email or password.
                                                        </div>
                                                    </c:if>

                                                    <c:if test="${param.logout != null}">
                                                        <div class="my-2" style="color: green;">Đăng xuất thành công
                                                        </div>
                                                    </c:if>

                                                    <div class="form-floating mb-3">
                                                        <input class="form-control" id="inputEmail" type="email"
                                                            placeholder="name@example.com" name="username" />
                                                        <label for="inputEmail">Email address</label>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <input class="form-control" id="inputPassword" type="password"
                                                            placeholder="Password" name="password" />
                                                        <label for="inputPassword">Password</label>
                                                    </div>
                                                    <div>
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                    </div>

                                                    <div
                                                        class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                        <button type="submit" class="btn btn-primary">Login</button>
                                                    </div>
                                                </form>
                                            </div>

                                            <div class="social-login">
                                                <a href="/oauth2/authorization/google" class="btn btn-danger mb-2">
                                                    <i class="fab fa-google"></i> Đăng nhập bằng Google
                                                </a>
                                                <br>
                                                <a href="/oauth2/authorization/facebook" class="btn btn-primary">
                                                    <i class="fab fa-facebook-f"></i> Đăng nhập bằng Facebook
                                                </a>
                                            </div>

                                            <div class="card-footer text-center py-3">
                                                <div class="small"><a href="/register">Need an account? Sign up!</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>

                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>