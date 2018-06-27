<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/resource/images/icons/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/animate/animate.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/css-hamburgers/hamburgers.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/animsition/css/animsition.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/select2/select2.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/daterangepicker/daterangepicker.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/util.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/main.css"/>"/>
</head>
<body>

<div class="limiter">
    <div class="container-login100" style="background-image: url('/resource/images/bg-01.jpg');">
        <div class="wrap-login100 p-l-110 p-r-110 p-t-62 p-b-33">
            <form class="login100-form validate-form flex-sb flex-w">
					<span class="login100-form-title">
						Sign In
					</span>

                <div class="p-t-31 p-b-9">
						<span class="txt1">
							Username
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="text" name="username" >
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-13 p-b-9">
						<span class="txt1">
							Password
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Password is required">
                    <input class="input100" type="password" name="password" >
                    <span class="focus-input100"></span>
                </div>

                <div class="container-login100-form-btn m-t-17">
                    <button class="login100-form-btn">
                        Sign In
                    </button>
                </div>

                <div class="w-full text-center p-t-55">
						<span class="txt2">
							Not a member?
						</span>

                    <a href="#" class="txt2 bo1">
                        Sign up now
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>


<%--<div id="dropDownSelect1"></div>--%>

<%--<script src="<c:url value="/resource/vendor/jquery/jquery-3.2.1.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/animsition/js/animsition.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/bootstrap/js/popper.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/bootstrap/js/bootstrap.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/select2/select2.min.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/daterangepicker/daterangepicker.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/vendor/countdowntime/countdowntime.js"/>"></script>--%>
<%--<script src="<c:url value="/resource/js/main.js"/>"></script>--%>

</body>
</html>