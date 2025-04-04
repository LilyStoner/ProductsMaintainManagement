<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/pages-sign-in.html" />

        <title>Sign In | AdminKit Demo</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>

    <body>
        <main class="d-flex w-100">
            <div class="container d-flex flex-column">
                <div class="row vh-100">
                    <div class="col-sm-10 col-md-8 col-lg-6 col-xl-5 mx-auto d-table h-100">
                        <div class="d-table-cell align-middle">

                            <div class="text-center mt-4">
                                <h1 class="h2">Welcome back!</h1>
                                <p class="lead">
                                    Sign in to your account to continue
                                </p>
                            </div>

                            <div class="card">
                                <div class="card-body">
                                    <div class="m-sm-3">
                                        <c:set var="cookie" value="pageContext.cookies"/>
                                        <form action="login" method="post">
                                            <div class="mb-3">
                                                <label class="form-label">Username</label>
                                                <input class="form-control form-control-lg"  name="username" placeholder="Enter your username" value="${cookie.cusername.value}" />
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Password</label>
                                                <input class="form-control form-control-lg" type="password" name="password" placeholder="Enter your password" value="${cookie.cpassword.value}" />
                                            </div>
                                            <div>
                                                <div class="form-check align-items-center">
                                                    <input id="customControlInline" type="checkbox" class="form-check-input" value="${cookie.crememberme.value}" name="remember-me" >
                                                    <label class="form-check-label text-small" for="customControlInline">Remember me</label>
                                                </div>
                                            </div>
                                            <div class="d-grid gap-2 mt-3">
                                                <button type="submit" class="btn btn-lg btn-primary">Sign in</button>
                                            </div>
                                            <a style="color: red">${requestScope.error}</a>
                                             <a style="color: red">${notification}</a>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center mb-3">
                                <a href="ForgotPasswordForm.jsp">Forgot password? |</a>
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid
                                   &redirect_uri=http://localhost:9999/MaintenanceSystem/login-google&response_type=code
                                   &client_id=64066072674-psh6a6q5kh91ch90urkb8tqbh0o6drhr.apps.googleusercontent.com&approval_prompt=force">Login With Google</a>


                            </div>

                        </div>
                    </div>
                </div>
        </main>

        <script src="js/app.js"></script>

    </body>

</html>